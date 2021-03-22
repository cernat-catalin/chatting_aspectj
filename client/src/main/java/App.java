import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

public class App extends Application {

    private final Scene loginScene;
    private final Scene chatScene;
    private ClientState clientState;
    private ChatClient client;

    public App() {
        loginScene = loginScene();
        chatScene = chatScene();
    }

    public void startServer() {
        this.clientState = new ClientState();
        final String hostname = "localhost";
        final int port = 8000;

        this.client = new ChatClient(hostname, port, clientState);
        client.start();
    }

    @Override
    public void start(Stage stage) {
        final Hello hello = new Hello();
        hello.sayHello();

        startServer();

        final Scene loginScene = loginScene();

        stage.setScene(loginScene);
        stage.show();
    }

    @Override
    public void stop() {
//        clientState.setShouldQuit(true);
        client.stop();
    }

    public Stage currentStage() {
        return (Stage) Stage.getWindows().filtered(Window::isShowing).get(0);
    }

    public Scene chatScene() {
        final GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        final Scene scene = new Scene(grid, 800, 275);

        final TextArea textArea = new TextArea();
        grid.add(textArea, 0, 0);

        final TextField textField = new TextField();
        grid.add(textField, 0, 1);

        final Button sendBtn = new Button("Send");
        final Button logoutBtn = new Button("Log out");

        final HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(sendBtn);
        hbBtn.getChildren().add(logoutBtn);
        grid.add(hbBtn, 1, 4);

        sendBtn.setOnAction(e -> {
            final String text = textField.getText();
            clientState.addMessageToSend(text);
            textField.clear();
        });

        logoutBtn.setOnAction(e -> {
            currentStage().setScene(loginScene);
        });

        return scene;

    }

    public Scene loginScene() {
        final GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        final Scene scene = new Scene(grid, 300, 275);

        final Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        final Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        final TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        final Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        final PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        final Button btn = new Button("Sign in");
        final HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        btn.setOnAction(e -> currentStage().setScene(chatScene));

        return scene;
    }
}
