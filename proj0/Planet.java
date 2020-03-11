public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    private static final double g = 6.67E-11;


    public Planet (double xP, double yP, double xV,
              double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }


    public Planet (Planet p) {
       xxPos = p.xxPos;
       yyPos = p.yyPos;
       xxVel = p.xxVel;
       yyVel = p.yyVel;
       mass = p.mass;
       imgFileName = p.imgFileName;
    }


    public double calcDistance(Planet p){
        double dx = p.xxPos - this.xxPos;
        double dy = p.yyPos - this.yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }


    public double calcForceExertedBy(Planet p){
        double r = this.calcDistance(p);
        double force = g * this.mass * p.mass / (r * r);
        return force;
    }


      public double calcForceExertedByX(Planet p){
          double dx = p.xxPos - this.xxPos;
          double dy = p.yyPos - this.yyPos;
          double force = this.calcForceExertedBy(p);
          double r = this.calcDistance(p);
          double fX = force * dx / r;
          return fX;
      }


      public double calcForceExertedByY(Planet p){
          double dx = p.xxPos - this.xxPos;
          double dy = p.yyPos - this.yyPos;
          double force = this.calcForceExertedBy(p);
          double r = this.calcDistance(p);
          double fY = force * dy / r;
          return fY;
      }


        public double calcNetForceExertedByX(Planet[] allPlants){
            double fX = 0.0;
            for (int i = 0; i < allPlants.length; i = i + 1){
                if (this.equals(allPlants[i])){
                    continue;
                }
                fX = fX + this.calcForceExertedByX(allPlants[i]);
            }
            return fX;
        }


        public double calcNetForceExertedByY(Planet[] allPlants){
            double fY = 0.0;
            for (Planet p: allPlants){
                if (!this.equals(p)){
                    fY = fY + this.calcForceExertedByY(p);
                    }
                }
            return fY;
        }


        public void update(double dt, double fX, double fY){
            this.xxVel += dt * fX / this.mass;
            this.yyVel += dt * fY / this.mass;
            this.xxPos += this.xxVel * dt;
            this.yyPos += this.yyVel * dt;
        }




        public void draw(){
            StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
        }







}
