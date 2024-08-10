public enum CellState {
    EMPTY(" "),
    X("X"),
    O("O");

    private final String stringRepresentation;

    CellState(String stringRepresentation) {
         this.stringRepresentation = stringRepresentation;
    }

    public String toString() {
        return stringRepresentation;
    }

    public static CellState getByString(String representation) throws IllegalArgumentException {
        if (!representation.equals(" "))
            representation = representation.trim().toUpperCase();

        for (CellState state: CellState.values()) {
            if (state.toString().equals(representation))
                return state;
        }

        throw new IllegalArgumentException("There is no cell state with such representation here: " + representation);
    }
}
