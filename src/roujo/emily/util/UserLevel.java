package roujo.emily.util;

public enum UserLevel {
	Owner(10), Super(5), Regular(0), BlackListed(-1);

	private int rank;

	private UserLevel(int rank) {
		this.rank = rank;
	}

	public int getRank() {
		return rank;
	}

}
