package TowerDefense.Stages;

import TowerDefense.Stages.base.GameStage;

public class Stage2 extends GameStage {
	public Stage2(int levelDificulty) {
		super(2, levelDificulty);
		super.coins = 700 - levelDificulty * 100;
	}
}
