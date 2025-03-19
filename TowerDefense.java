import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
// import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
// import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import java.util.ArrayList;

public class TowerDefense extends Application {
    
    // Dimensions du jeu
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static int initialisation=0;
    private ArrayList<tour> tours = new ArrayList<>();

    
    private double clicX = 0, clicY = 0; 
    class tour {
        int menu=0;
        int type=0;
        int lvl=0;

        public tour(int a ,int b ,int c){
            this.menu=a;
            this.type=b;
            this.lvl=c;
        }
        public void setMenu(int a){
            this.menu=a;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // Créer la fenêtre
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Scene scene = new Scene(new javafx.scene.layout.StackPane(canvas));
        
        //coordonés du clic
        scene.setOnMouseClicked(event -> {
            clicX = event.getX();
            clicY = event.getY();
            System.out.println("Clic détecté à : " + clicX + ", " + clicY);
        });
        
        // Détecter les touches
        // scene.setOnKeyPressed(event -> {
        //     if (event.getCode() == KeyCode.Q && paddleX > 0) {
        //         paddleX -= 40;
        //     } else if (event.getCode() == KeyCode.D && paddleX < WIDTH - PADDLE_WIDTH) {
        //         paddleX += 40;
        //     }
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
        primaryStage.setTitle("towet defence");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initialisation(){
        for (int i=0;i<4;i++){
                tours.add(new tour(0, 0, 0));
            }
    }

    private void update() {
        if(initialisation==0){
            initialisation();
            initialisation++;
        }
        
        if (200<clicX && clicX<240){
            if(40<clicY && clicY<80){
                tours.get(0).setMenu(1); 
            }
            if(140<clicY && clicY<180){
                tours.get(1).setMenu(1); 
            }
            if(240<clicY && clicY<280){
                tours.get(2).setMenu(1); 
            }
            if(340<clicY && clicY<380){
                tours.get(3).setMenu(1); 
            }   
            }
            else{
                for (int j=0; j<tours.size(); j++){
                    tours.get(j).setMenu(0);
                }
        }
    }

    private void draw(GraphicsContext gc) {
        // Fond noir
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        
        //map
        gc.setFill(Color.WHITE);
        gc.fillRect(250, 350, 250, 50);
        gc.fillRect(250, 0, 50, 350);
        gc.fillRect(500, 350, 50, 500);
        int posT=40;
        for (tour tour : tours) {
           if(tour.menu ==1){
            gc.setFill(Color.RED);
           }
           else{
            gc.setFill(Color.BLUE);
           }
            gc.fillOval(200, posT, 40, 40);
            posT = posT + 100;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
