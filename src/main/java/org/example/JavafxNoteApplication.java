package org.example;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class JavafxNoteApplication extends Application {

    private ConfigurableApplicationContext context;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        ApplicationContextInitializer<GenericApplicationContext> initializer =
                applicationContext -> {
                    applicationContext.registerBean(Application.class, () -> JavafxNoteApplication.this);
                    applicationContext.registerBean(Parameters.class, this::getParameters);
                    applicationContext.registerBean(HostServices.class, this::getHostServices);
                };

        this.context = new SpringApplicationBuilder()
                .sources(Launcher.class)
                .initializers(initializer)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.context.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() throws Exception {
        this.context.close();
        Platform.exit();
    }
}

@Component
class StageInitializer implements ApplicationListener<StageReadyEvent> {
    private final ApplicationContext applicationContext;

    StageInitializer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        try {
            Stage stage = stageReadyEvent.getStage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/main-view.fxml"));
            fxmlLoader.setControllerFactory(this.applicationContext::getBean);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 1920, 1080);
            stage.setScene(scene);
            stage.setTitle("JavaFx: Note App");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class StageReadyEvent extends ApplicationEvent {
    private final Stage stage;

    StageReadyEvent(Stage stage) {
        super(stage);
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
