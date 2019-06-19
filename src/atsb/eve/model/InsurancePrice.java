package atsb.eve.model;

import java.util.ArrayList;
import java.util.List;

public class InsurancePrice {

	private int typeId;
	private List<Level> levels;

	public InsurancePrice() {
		this.levels = new ArrayList<Level>();
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public List<Level> getLevels() {
		return levels;
	}

	public void setLevels(List<Level> levels) {
		this.levels = levels;
	}

	public static class Level {
		private String name;
		private float cost;
		private float payout;

		public Level() {
		}

		public String getName() {
			return name;
		}

		public float getCost() {
			return cost;
		}

		public float getPayout() {
			return payout;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setCost(float cost) {
			this.cost = cost;
		}

		public void setPayout(float payout) {
			this.payout = payout;
		}
	}

}
