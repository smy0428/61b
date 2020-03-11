public class NBody {
    public static double readRadius(String filename){
        In in = new In(filename);
        int N = in.readInt();
        double R = in.readDouble();

        return R;
    }


    public static Planet[] readPlanets(String filename){
        In in = new In(filename);
        int N = in.readInt();
        double R = in.readDouble();
        Planet[] planets = new Planet[N];
        int i = 0;
        while ( i < N ){
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            planets[i] = new Planet (xP, yP, xV, yV, m, img);
            i += 1;
        }

        return planets;
    }



    public static void main(String[] args) {
          double T = Double.parseDouble(args[0]);
          double dt = Double.parseDouble(args[1]);
          String filename = args[2];
          double universeRadius = readRadius(filename);
          Planet[] planets = readPlanets(filename);

          StdDraw.setScale(-1 * universeRadius, universeRadius);
          StdDraw.clear();
          /* Draws a background using this image.
           * yes, it works! */
          StdDraw.picture(0, 0, "images/starfield.jpg");

          for (Planet planet: planets){
              planet.draw();
          }

          StdDraw.enableDoubleBuffering();
          int len = planets.length;
          double[] xForces = new double [len];
          double[] yForces = new double [len];
          for (double t = 0; t < T; t = t + dt){
              int i = 0;
              for (Planet planet: planets){
                  xForces[i] = planet.calcNetForceExertedByX(planets);
                  yForces[i] = planet.calcNetForceExertedByY(planets);
                  i += 1;
              }
              int k = 0;
              for (Planet planet: planets){
                  planet.update(dt, xForces[k], yForces[k]);
                  k += 1;
              }

              StdDraw.picture(0, 0, "images/starfield.jpg");
              for (Planet planet: planets){
                  planet.draw();
              }
              StdDraw.show();
              StdDraw.pause(10);
          }

          StdOut.printf("%d/n", len);
          StdOut.printf("%.2e/n", universeRadius);
          for (Planet planet: planets){
              StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %11s/n",
              planet.xxPos, planet.yyPos, planet.xxVel,
              planet.yyVel, planet.mass, planet.imgFileName);
          }

      }
}
