

public class LevelData {
	private int lives;
	private int score;
	public LevelData() {
		lives = 3;
		score = 0;
	}
	public int getScore() {
		return score;
	}
	public void increaseScore(int score) {
		this.score += score;
	}
	public int getLives() {
		return lives;
	}
	public void decrementLives() {
		lives--;
	}
	public void incrementLives() {
		lives++;
	}
}
