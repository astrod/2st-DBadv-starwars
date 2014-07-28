import java.util.ArrayList;
import java.util.Random;


public class GameMaster implements java.lang.Runnable{
	private int threadNum;
	ArrayList<User> userArray;
	ArrayList<Galaxy> UniArray;
	int UserNum;
	
	public GameMaster() {
		
	}
	
	public GameMaster(int num) {
		UserNum = num;
		UniArray = new ArrayList<Galaxy>();
		userArray = new ArrayList<User>();
		Galaxy uni1;
		for(int i=0; i<4; i++) {
			uni1 = new Galaxy(10000);
			UniArray.add(uni1);
		}
		
	}
	
	public void setLog(int userNum, int ourPlant, int galaxyNum, int damage, int curHP) {
		System.out.println(userNum + "번 유저가(소속은 " + ourPlant + "인) " + galaxyNum + "우주를 공격하여" + damage + "만큼 피해를 입혔습니다. 남은 HP는 " + curHP + "입니다.");
		//$번 유저가 $번 행성을 공격하여 $만큼 피해를 입혔습니다.
	}
	
	public void addUser() {
		for(int i=0; i<UserNum; i++) {
			int galNum = i % 4;
			User u = new User(galNum, i);
			userArray.add(u);
		}		
	}
	
	public void run() {
		Random random = new Random();
		
		while(true) {
			synchronized (this) {
				User curUser = userArray.get(random.nextInt(UserNum));
				int curHP = curUser.attack(curUser.userNum, UniArray);
				if(curHP <= 0) {
					System.out.println("행성의 hp가 " + curHP + "입니다. 게임을 종료하겠습니다.");
					break;				
				}
			}
			
			try {
				Thread.sleep(10);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
