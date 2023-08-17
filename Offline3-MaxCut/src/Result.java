public class Result {
    int constructPhaseCost;

    double localSearchIterations;
    double localSearchAvgCost;

    int graspIterations;
    int graspCost;

    public Result(int constructPhaseCost, double localSearchIterations, double localSearchAvgCost) {
        this.constructPhaseCost = constructPhaseCost;
        this.localSearchIterations = localSearchIterations;
        this.localSearchAvgCost = localSearchAvgCost;
    }

    public Result(int constructPhaseCost, double localSearchIterations, double localSearchAvgCost, int graspIterations, int graspCost) {
        this.constructPhaseCost = constructPhaseCost;
        this.localSearchIterations = localSearchIterations;
        this.localSearchAvgCost = localSearchAvgCost;
        this.graspIterations = graspIterations;
        this.graspCost = graspCost;
    }
}
