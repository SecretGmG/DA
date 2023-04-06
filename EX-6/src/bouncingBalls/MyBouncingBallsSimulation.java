package bouncingBalls;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class MyBouncingBallsSimulation extends BouncingBallsSimulation{

    LinkedList<Ball>[][] grid;

    /**
     * Initializes the simulation.
     *
     * @param w width of simulation window.
     * @param h height of simulation window.
     * @param n number of balls.
     * @param r radius of balls.
     * @param v initial velocity of balls.
     */
    public MyBouncingBallsSimulation(int w, int h, int n, float r, float v, int factor) {
        super(w, h, n, r, v);
        initializeGrid(w, h, n, r, v, factor);
    }
    private void initializeGrid(int w, int h, int n, float r, float v, int factor){
        //These simply ensure that we never have to check any grid cells more than one grid space away!
        int minW =  (int) Math.ceil(w / r);
        int minH = (int) Math.ceil(h / r);


        grid = new LinkedList[Math.max(minW, minW * factor)][Math.max(minH, minH * factor)];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = new LinkedList<>();
            }
        }

        System.out.printf("grid size: %d, %d", grid.length, grid[0].length);

        for(Ball ball : balls){
            int x = getX(ball);
            int y = getY(ball);
            grid[x][y].push(ball);
        }
    }

    int getX(Ball ball){
        return (int) ((ball.x / w) * grid.length);
    }
    int getY(Ball ball){
        return (int) ((ball.y / h) * grid[0].length);
    }

    /**
     * The simulation loop.
     */
    @Override
    public void run()
    {
        // Set up timer.
        int c = 0;
        Timer timer = new Timer();
        timer.reset();


        // Loop forever (or until the user closes the main window).
        while(true)
        {
            // Run one simulation step.
            Iterator<Ball> it = balls.iterator();

            // Iterate over all balls.
            while(it.hasNext())
            {
                Ball ball = it.next();

                // Move the ball.
                ball.move();

                // Handle collisions with boundaries.
                if(ball.doesCollide((float)w,0.f,-1.f,0.f))
                    ball.resolveCollision((float)w,0.f,-1.f,0.f);
                if(ball.doesCollide(0.f,0.f,1.f,0.f))
                    ball.resolveCollision(0.f,0.f,1.f,0.f);
                if(ball.doesCollide(0.f,(float)h,0.f,-1.f))
                    ball.resolveCollision(0.f,(float)h,0.f,-1.f);
                if(ball.doesCollide(0.f,0.f,0.f,1.f))
                    ball.resolveCollision(0.f,0.f,0.f,1.f);

                int x = getX(ball);
                int y = getY(ball);


                // Handle collisions with other balls.
                handleBallCollisionsNear(x,y,ball);
                x = getX(ball);
                y = getY(ball);

                //I don't necessarily delete the reference of the ball in it's old grid cell, but it should get cleaned up eventually
                //I think this is faster than checking everytime because most of the time the old reference will be in the surrounding cells anyways
                grid[x][y].push(ball);
            }

            // Trigger update of display.
            repaint();

            // Print time per simulation step.
            c++;
            if(c==500)
            {
                System.out.printf("myBouncingBalls.Timer per simulation step: %fms\n", (float)timer.timeElapsed()/(float)c);
                timer.reset();
                c = 0;
            }
        }
    }

    /**
     * handles all collisions of other balls with the given ball at the cell w, h and all surrounding cells
     * any balls not belonging in their grid cells will be removed
     * @param x
     * @param y
     * @param ball
     */
    public void handleBallCollisionsNear(int x, int y, Ball ball){
        for(int i = -1; i<=1; i++){
            for(int j = -1; j<=1; j++){
                handleBallCollisionsAt(x+i,y+i,ball);
            }
        }
    }

    /**
     * handles all collision of the given ball with other balls in the cell w, h
     * any ball does not belong in this cell it will be removed
     * @param x
     * @param y
     * @param ball
     */
    public void handleBallCollisionsAt(int x, int y, Ball ball){
        if(x<0 || x>=grid.length || y<0 || y>=grid[0].length)
            return;
        ListIterator<Ball> iter = grid[x][y].listIterator();
        while (iter.hasNext()){
            Ball otherBall = iter.next();
            int otherX = getX(otherBall);
            int otherY = getY(otherBall);
            if(otherX != x || otherY != y){
                iter.remove();
            }else if(ball != otherBall && ball.doesCollide(otherBall)){
                ball.resolveCollision(otherBall);
            }
        }
    }

}
