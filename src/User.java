import java.util.ArrayList;
import java.util.Random;

public class User {
	private ArrayList<Integer> shipArray;
	int galaxyNum;
	int userNum;
	
	public User(int galaxyNum, int userNum) {
		Random random = new Random();
		this.galaxyNum = galaxyNum;
		this.userNum = userNum;
		shipArray = new ArrayList<Integer>();
		
		for(int i=0; i<10; i++) {
			shipArray.add(random.nextInt(96) + 5);
		}
	}
	
	public int attack(int userNum, ArrayList<Galaxy> array) {
		Random random = new Random();
		GameMaster gMaster = new GameMaster();
		int attackLocation = 0;
		Galaxy attack;
		
		for(int i=0; i<10; i++) {
			while(true) {
				attackLocation = random.nextInt(4);
				if(attackLocation != galaxyNum) break;
			}
			
			attack = array.get(attackLocation);
			attack.setHP(attack.getHP()-shipArray.get(i));
			if(attack.getHP() <= 0) return attack.getHP();
			gMaster.setLog(userNum, galaxyNum, attackLocation, shipArray.get(i), attack.getHP());
		}
		
		return 1;
	}
	
}
