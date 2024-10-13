package TowerDefense.Stages;

import TowerDefense.Stages.base.GameStage;

public class Stage3 extends GameStage {
	public Stage3(int levelDificulty) {
		super(3, levelDificulty);
		super.coins = 800 - levelDificulty * 100;
	}
}
