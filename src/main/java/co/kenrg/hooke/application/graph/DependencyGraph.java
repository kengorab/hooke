package co.kenrg.hooke.application.graph;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import co.kenrg.hooke.application.iface.DependencyInserter;
import com.google.common.collect.Sets;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import org.apache.commons.lang3.tuple.Pair;

public class DependencyGraph {
    public static Graph<DependencyUnit> buildDependencyGraph(List<DependencyUnit> dependencyUnits) {
        Map<Class, DependencyUnit> dependencyUnitMap = dependencyUnits.stream()
            .collect(toMap(unit -> unit.providesClass, Function.identity()));

        MutableGraph<DependencyUnit> graph = GraphBuilder.directed().build();
        for (DependencyUnit unit : dependencyUnits) {
            graph.addNode(unit);
            for (Pair<String, Class> dependency : unit.dependencies) {
                DependencyUnit depUnit = dependencyUnitMap.get(dependency.getValue());
                graph.putEdge(unit, depUnit);
            }
        }

        return graph;
    }

    public static void resolveDependencyGraph(Graph<DependencyUnit> graph, DependencyInserter dependencyInserter) {
        Set<DependencyUnit> nodesToProcess = graph.nodes().stream()
            .filter(node -> graph.outDegree(node) == 0) // Start at leaves
            .collect(toSet());

        Set<DependencyUnit> processedNodes = Sets.newHashSet();
        while (!nodesToProcess.isEmpty()) {
            for (DependencyUnit node : nodesToProcess) {
                dependencyInserter.insert(node.providesClass, node.getInstance());
            }

            processedNodes.addAll(nodesToProcess);

            Set<DependencyUnit> nextLevel = Sets.newHashSet();
            for (DependencyUnit node : nodesToProcess) {
                Set<DependencyUnit> dependants = graph.predecessors(node).stream()
                    .filter(dep -> !processedNodes.contains(dep))
                    .collect(toSet());
                nextLevel.addAll(dependants);
            }
            nodesToProcess.clear();
            nodesToProcess.addAll(nextLevel);
        }
    }
}
