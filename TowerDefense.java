import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
// import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
// import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import java.util.ArrayList;

public class TowerDefense extends Application {

    // Dimensions du jeu
    private static int posInitY = 0;
    // private static int posInitX =0;
    private static int or = 100;
    private static int vie = 20;
    private static int lanceB = 0;
    private static int cooldownULT = 0;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static int initialisation = 0;
    private ArrayList<tour> tours = new ArrayList<>();
    private ArrayList<enemi> enemis = new ArrayList<>();
    private ArrayList<projectile> projectiles = new ArrayList<>();

    private double clicX = 0, clicY = 0;
    private double overX = 0, overY = 0;

    class tour {
        private int menu = 0;
        private int type = 0;
        private int lvl = 0;
        private int posX = 0;
        private int posY = 0;
        private int cooldown = 0;

        public tour(int a, int b) {
            this.posX = a;
            this.posY = b;
        }

        public void setMenu(int a) {
            this.menu = a;
        }

        public void setLvl(int a) {
            this.lvl = a;
        }

        public void setType(int a) {
            this.type = a;
        }

        public void setCooldown(int a) {
            this.cooldown = a;
        }
    }

    class enemi {
        private double positionX = 0;
        private double positionY = 0;
        private int type = 0;
        private int hp = 2;
        private double vitesse = 0;
        private int or = 0;

        public enemi(double a, double b, int c) {
            this.positionX = a;
            this.positionY = b;
            this.type = c;
        }

        public void setPosX(double a) {
            this.positionX = a;
        }

        public void setPosY(double b) {
            this.positionY = b;
        }

        public void setHp(int c) {
            this.hp = c;
        }

        public void initType(int a, double b, int c) {
            this.hp = a;
            this.vitesse = b;
            this.or = c;
        }
    }

    class projectile {
        private double positionX = 0;
        private double positionY = 0;
        private int cible = 0;
        private int depart = 0;
        private int touche = 0;

        public projectile(double a, double b, int c, int d) {
            this.positionX = a;
            this.positionY = b;
            this.cible = c;
            this.depart = d;
        }

        public void setPos(double a, double b) {
            this.positionX = a;
            this.positionY = b;
        }

        public void setTouche(int a) {
            this.touche = a;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // Créer la fenêtre
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Scene scene = new Scene(new javafx.scene.layout.StackPane(canvas));

        // coordonés du clic
        scene.setOnMouseClicked(event -> {
            clicX = event.getX();
            clicY = event.getY();
            System.out.println("Clic détecté à : " + clicX + ", " + clicY);
        });
        scene.setOnMouseMoved(event -> {
            overX = event.getX();
            overY = event.getY();

        });
        // Détecter les touches
        // scene.setOnKeyPressed(event -> {
        // if (event.getCode() == KeyCode.Q && paddleX > 0) {
        // paddleX -= 40;
        // } else if (event.getCode() == KeyCode.D && paddleX < WIDTH - PADDLE_WIDTH) {
        // paddleX += 40;
        // }
        // });

        // Boucle du jeu
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw(gc);
            }
        }.start();

        // Configurer la fenêtre
        primaryStage.setTitle("TowerDefence");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initialisation() {

        for (int i = 0; i < 3; i++) {
            tours.add(new tour(190, 30 + 130 * i));
            tours.add(new tour(190 + 130 * i, 420));
            tours.add(new tour(320, 30 + 130 * i));
            tours.add(new tour(580, 420 + 130 * i));
        }
        for (int i = 0; i < 2; i++) {
            tours.add(new tour(450 + 130 * i, 290));
            tours.add(new tour(450, 550 + 130 * i));
        }

        for (int i = 0; i < 18; i++) {
            enemis.add(new enemi(265, 0, 1));
            enemis.get(enemis.size() - 1).setPosY(posInitY);
            posInitY = posInitY - 80;
            System.out.println(enemis.get(i).positionY);
        }

        for (int i = 0; i < 4; i++) {
            enemis.add(new enemi(265, 0, 2));
            enemis.get(enemis.size() - 1).setPosY(posInitY);
            posInitY = posInitY - 80;

        }
        for (int i = 0; i < 4; i++) {
            enemis.add(new enemi(265, 0, 3));
            enemis.get(enemis.size() - 1).setPosY(posInitY);
            ;
            posInitY = posInitY - 80;
        }

        for (int i = 0; i < enemis.size(); i++) {
            if (enemis.get(i).type == 1) {
                enemis.get(i).initType(1, 0.4, 5);
            } else if (enemis.get(i).type == 2) {
                enemis.get(i).initType(2, 0.4, 10);
            }
            if (enemis.get(i).type == 3) {
                enemis.get(i).initType(1, 0.8, 15);
            }

        }
        System.out.println("initialisation...");
    }

    private void update() {
        if (initialisation == 0) {
            initialisation();
            initialisation++;
            for (int i = 0; i < enemis.size(); i++) {
                System.out.println(enemis.get(i).positionY);
            }
        }
        // déplacement des ennemis
        for (int i = 0; i < enemis.size(); i++) {

            if (enemis.get(i).positionY < 365 && enemis.get(i).hp >= 1) {
                enemis.get(i).setPosY(enemis.get(i).positionY + enemis.get(i).vitesse);
            } else if (enemis.get(i).positionX < 515 && enemis.get(i).hp >= 1) {
                enemis.get(i).setPosX(enemis.get(i).positionX + enemis.get(i).vitesse);
            } else if (enemis.get(i).positionY < 800 && enemis.get(i).hp >= 1) {
                enemis.get(i).setPosY(enemis.get(i).positionY + enemis.get(i).vitesse);
            } else {
                enemis.get(i).setHp(0);
            }

        }
        // tire de la tour
        for (int i = 0; i < tours.size(); i++) {
            if (tours.get(i).lvl > 0) {
                for (int j = 0; j < enemis.size(); j++) {
                    double calcx = enemis.get(j).positionX - tours.get(i).posX;
                    double calcy = enemis.get(j).positionY - tours.get(i).posY;
                    double distance = Math.sqrt(calcx * calcx + calcy * calcy);
                    if (distance < 135 && enemis.get(j).hp > 0 && tours.get(i).cooldown < 0) {

                        tours.get(i).setCooldown(500);
                        projectiles.add(new projectile(tours.get(i).posX, tours.get(i).posY, j, i));

                    }
                }
            }
        }
        // touche la cible
        for (int i = 0; i < projectiles.size(); i++) {
            if (projectiles.get(i).touche == 0) {
                if (projectiles.get(i).positionX <= enemis.get(projectiles.get(i).cible).positionX + 5
                        && projectiles.get(i).positionX >= enemis.get(projectiles.get(i).cible).positionX - 5) {
                    if (projectiles.get(i).positionY <= enemis.get(projectiles.get(i).cible).positionY + 5
                            && projectiles.get(i).positionY >= enemis.get(projectiles.get(i).cible).positionY - 5) {
                        projectiles.get(i).setTouche(1);
                        enemis.get(projectiles.get(i).cible).setHp(enemis.get(projectiles.get(i).cible).hp - 1);
                        or = or + enemis.get(i).or;
                        System.out.println(or);
                    }
                }
            }
        }
        // selection tour
        for (int i = 0; i < tours.size(); i++) {
            if (tours.get(i).posX < clicX && clicX < tours.get(i).posX + 40) {
                if (tours.get(i).posY < clicY && clicY < tours.get(i).posY + 40) {
                    if (tours.get(i).lvl == 0) {
                        tours.get(i).setMenu(1);
                    } else {
                        tours.get(i).setMenu(2);
                    }
                }
            }
        }
        // boutons menu
        // bouton fermer
        for (int i = 0; i < tours.size(); i++) {
            if (tours.get(i).menu >= 1) {
                if (tours.get(i).posX + 10 < clicX && clicX < tours.get(i).posX + 30) {
                    if (tours.get(i).posY + 50 < clicY && clicY < tours.get(i).posY + 70) {
                        tours.get(i).setMenu(0);
                    }
                }
            }
        }
        // bouton type
        for (int i = 0; i < tours.size(); i++) {
            if (tours.get(i).menu == 1) {
                if (tours.get(i).posY + 10 < clicY && clicY < tours.get(i).posY + 30) {
                    if (tours.get(i).posX + 50 < clicX && clicX < tours.get(i).posX + 70 && or > 50) {
                        tours.get(i).setType(1);
                        System.out.println("archer");
                        tours.get(i).setMenu(2);
                        tours.get(i).setLvl(1);
                        clicX = 0;
                        clicY = 0;
                        or = or - 50;
                    }
                    if (tours.get(i).posX - 30 < clicX && clicX < tours.get(i).posX - 10 && or > 80) {
                        tours.get(i).setType(2);
                        System.out.println("mage");
                        tours.get(i).setMenu(2);
                        tours.get(i).setLvl(1);
                        clicX = 0;
                        clicY = 0;
                        or = or - 80;
                    }
                }
            }
        }
        // bouton ameliorer
        for (int i = 0; i < tours.size(); i++) {
            if (tours.get(i).lvl > 0) {
                if (tours.get(i).posY + 10 < clicY && clicY < tours.get(i).posY + 30) {
                    if (tours.get(i).posX + 50 < clicX && clicX < tours.get(i).posX + 70
                            && or > tours.get(i).lvl * 30) {
                        or = or - tours.get(i).lvl * 30;
                        tours.get(i).setLvl(tours.get(i).lvl + 1);
                        System.out.println("lvl" + tours.get(i).lvl);
                    }
                }
            }
        }
        // bouton vendre
        for (int i = 0; i < tours.size(); i++) {
            if (tours.get(i).lvl > 0) {
                if (tours.get(i).posY + 10 < clicY && clicY < tours.get(i).posY + 30) {
                    if (tours.get(i).posX - 30 < clicX && clicX < tours.get(i).posX - 10) {
                        tours.get(i).setLvl(0);
                        tours.get(i).setMenu(1);
                        tours.get(i).setType(0);
                        System.out.println("lvl" + tours.get(i).lvl);
                    }
                }
            }
        }
        // bouton bonus
        if (clicX > 50 && clicX < 100 && clicY > 50 && clicY < 80 && cooldownULT == 0 && lanceB == 0) {
            // for (int i=0; i<enemis.size(); i++){
            lanceB = 1;
            // if(enemis.get(i).positionY>0){
            // enemis.get(i).setHp(0);
            // cooldownULT=5000;
            // }
            // }
            clicX = 0;
            clicY = 0;
        }
        if (clicX > 50 && clicX < 100 && clicY > 50 && clicY < 80 && cooldownULT == 0 && lanceB == 1) {
            lanceB = 0;

        } else if (lanceB == 1) {

            for (int i = 0; i < enemis.size(); i++) {
                double calcx = enemis.get(i).positionX - clicX;
                double calcy = enemis.get(i).positionY - clicY;
                double distance = Math.sqrt(calcx * calcx + calcy * calcy);

                if (distance < 75) {
                    lanceB = 0;
                    enemis.get(i).setHp(0);
                    cooldownULT = 5000;
                }
            }
        }
        clicX = 0;
        clicY = 0;
        for (int i = 0; i < tours.size(); i++) {
            tours.get(i).setCooldown(tours.get(i).cooldown - 1);
        }
        if (cooldownULT > 0) {
            cooldownULT = cooldownULT - 1;
        }
        // perte de vie
        for (int i = 0; i < enemis.size(); i++) {
            if (enemis.get(i).positionY > 800 & enemis.get(i).hp == 1) {
                vie--;
            }
        }
    }

    private void draw(GraphicsContext gc) {
        // Fond noir
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        // map
        gc.setFill(Color.WHITE);
        gc.fillRect(250, 350, 250, 50);
        gc.fillRect(250, 0, 50, 350);
        gc.fillRect(500, 350, 50, 500);

        // tours
        for (tour tour : tours) {
            // menu
            if (tour.menu == 1) {
                gc.setFill(Color.GREEN);
                gc.fillOval(tour.posX - 5, tour.posY - 5, 50, 50);
                gc.fillRect(tour.posX - 30, tour.posY + 10, 20, 20);
                gc.fillRect(tour.posX + 50, tour.posY + 10, 20, 20);
                gc.fillRect(tour.posX + 10, tour.posY + 50, 20, 20);
                gc.setFill(Color.RED);
            }
            if (tour.menu == 2) {
                gc.setFill(Color.PINK);
                gc.fillOval(tour.posX - 5, tour.posY - 5, 50, 50);
                gc.fillRect(tour.posX - 30, tour.posY + 10, 20, 20);
                gc.fillRect(tour.posX + 50, tour.posY + 10, 20, 20);
                gc.fillRect(tour.posX + 10, tour.posY + 50, 20, 20);
                gc.setFill(Color.RED);
                gc.setStroke(Color.RED);
                gc.strokeOval(tour.posX - 105, tour.posY - 105, 250, 250);
            }
            if (tour.type == 0) {
                gc.setFill(Color.BLUE);
            }
            if (tour.type == 1) {
                gc.setFill(Color.YELLOW);
            }
            if (tour.type == 2) {
                gc.setFill(Color.PURPLE);
            }
            gc.fillOval(tour.posX, tour.posY, 40, 40);
        }
        // tir
        for (int i = 0; i < projectiles.size(); i++) {
            if (projectiles.get(i).touche == 0) {
                double a = (enemis.get(projectiles.get(i).cible).positionX - tours.get(projectiles.get(i).depart).posX)
                        / 20 + projectiles.get(i).positionX;
                double b = (enemis.get(projectiles.get(i).cible).positionY - tours.get(projectiles.get(i).depart).posY)
                        / 20 + projectiles.get(i).positionY;
                projectiles.get(i).setPos(a, b);
                gc.fillOval(projectiles.get(i).positionX, projectiles.get(i).positionY, 20, 20);
            }

        }

        // ennemis
        for (int i = 0; i < enemis.size(); i++) {

            if (enemis.get(i).hp == 1) {
                if (enemis.get(i).type == 3) {
                    gc.setFill(Color.YELLOW);
                } else {
                    gc.setFill(Color.PINK);
                }
                gc.fillRect(enemis.get(i).positionX, enemis.get(i).positionY, 20, 20);
            }

            if (enemis.get(i).hp > 1) {
                gc.setFill(Color.RED);
                gc.fillRect(enemis.get(i).positionX, enemis.get(i).positionY, 20, 20);

            }
        }
        // or
        gc.setStroke(Color.WHITE);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(23));
        gc.fillText("or:" + or, 55, 725);
        gc.strokeRect(50, 700, 100, 35);
        // bonus
        gc.setFill(Color.RED);
        gc.fillRect(50, 50, 50, 30);
        gc.setFill(Color.BLACK);
        gc.fillRect(50, 50, cooldownULT / 100, 30);
        gc.strokeRect(50, 50, 50, 30);
        // bonus activé
        if (lanceB == 1) {
            gc.setStroke(Color.YELLOW);
            gc.strokeOval(overX - 75, overY - 75, 150, 150);
        }

        // vie
        gc.setFill(Color.RED);
        gc.fillRect(50, 750, vie * 2.5, 30);
        gc.setFill(Color.BLACK);
        gc.strokeRect(50, 50, 50, 30);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
