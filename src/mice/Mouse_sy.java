package mice;

public class Mouse_sy {
	private int [][] map = {{0, 0, 0, 0, 0},
							{1, 0, 1, 1, 0},
							{0, 0, 1, 0, 0},
							{0, 1, 0, 1, 0},
							{0, 0, 0, 0, 0}};
	
	private int y_length = map.length;
	private int x_length = map[0].length;
	private int end_x = 4, end_y = 4;
	private int start_x = 0, start_y = 0;
	private int min;
	private int[][] dir = {{0,1}, {1,0}, {0, -1}, {-1, 0}};
	private int[][] other_route;
	private int[][] visited;
	private int top;
	public Mouse_sy() {
		min = 0;
		visited = new int[5][5];
		other_route = new int[100][2];
		top = -1;
		
	}

	private void printMap(int x, int y) {
		String goal = "▶";
		String road = "·";
		String block = "■";
		String mouse = "§";
		
		for (int i = 0; i < y_length; i++) {

			for (int j = 0; j < x_length; j++) {
				if (x == j && y == i) {
					System.out.print(mouse);
				} else if (end_x == j && end_y == i) {
					System.out.print(goal);
				} else if (map[i][j] == 0 ) {
					System.out.print(road);
				} else if (map[i][j] == 1) {
					System.out.print(block);
				} 
				
				System.out.print(" ");
			}
			System.out.println();
		}
	    System.out.println();

	}

	protected int route(int y, int x, int depth)
	{
		int able_x, able_y;
		int count = 0;
		visited[y][x]=2;
		
		
		if(x<0 || y<0 || y>=y_length || x>=x_length)    
	        return depth;
	    if(x==end_x && y == end_y) {
	    	if(depth<min)
	    		min = depth;
	    	return depth;	
	    }
	    
	    for(int i=0; i<4; i++) {
	    	able_x = x + dir[i][0];
            able_y = y + dir[i][1];
            if(able_x>=5 || able_y >=5 || able_x<0 || able_y<0)
            	continue;
            else if(map[able_y][able_x]==0) {
            	other_route[++top][0] = able_x;
            	other_route[top][1] = able_y;
            	break;	
            }else if(map[able_y][able_x]==2)
            	count++;
            if(count==4) {
            	top--;
            	depth-=2;
            }
	    }
	    
	    x = other_route[top][0];
    	y = other_route[top++][1];
    	
	    printMap(x, y);
	    return route(y, x, depth+1);
	
	}
	protected void show() {
		this.min = route(start_y,start_x,0);
		System.out.println("최단거리: " + min);
	}
	public static void main(String[] args) {
		Mouse_sy test = new Mouse_sy();
		test.show();		
	}
}
