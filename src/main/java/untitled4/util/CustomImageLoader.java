package untitled4.util;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class CustomImageLoader {

    private static final Logger LOGGER = Logger.getLogger("CustomImageLoader");
    private static final String ASSET_PATH = "textures/";

    private static final Map<String, Image> imageCache = new HashMap<>();
    private static final Map<String, AnimationChannel> animationCache = new HashMap<>();

    public static Image loadImage(String relativePath) {
        if (imageCache.containsKey(relativePath)) {
            return imageCache.get(relativePath);
        }

        try {
            Image image = FXGL.getAssetLoader().loadImage(ASSET_PATH + relativePath);
            imageCache.put(relativePath, image);
            LOGGER.info("Loaded image: " + relativePath);
            return image;
        } catch (Exception e) {
            LOGGER.warning("Failed to load image: " + relativePath + ". Showing placeholder");
            return createPlaceholderImage("Missing:\n" + relativePath, 128, 128);
        }
    }

    public static AnimationChannel loadSpritesheetAnimation(String relativePath, int frameCount, int frameWidth, int frameHeight, Duration duration) {
        if (animationCache.containsKey(relativePath)) {
            return animationCache.get(relativePath);
        }

        try {
            Image image = FXGL.getAssetLoader().loadImage(ASSET_PATH + relativePath);
            AnimationChannel channel = new AnimationChannel(image, frameCount, frameWidth, frameHeight, duration);
            animationCache.put(relativePath, channel);
            LOGGER.info("Loaded spritesheet animation: " + relativePath);
            return channel;
        } catch (Exception e) {
            LOGGER.warning("Failed to load spritesheet: " + relativePath + ". Showing placeholder");
            return new AnimationChannel(createPlaceholderImage("Missing:\n" + relativePath, frameWidth, frameHeight), frameCount, frameWidth, frameHeight, duration);
        }
    }

    public static Map<String, AnimationChannel> loadAllAnimations(String folder, int frameWidth, int frameHeight, Duration duration) {
        Map<String, AnimationChannel> animations = new HashMap<>();

        File dir = new File("src/main/resources/assets/" + ASSET_PATH + folder);

        if (!dir.exists() || !dir.isDirectory()) {
            LOGGER.warning("Directory does not exist: " + dir.getAbsolutePath());
            return animations;
        }

        for (File file : dir.listFiles()) {
            if (file.getName().endsWith(".png")) {
                String relativePath = folder + "/" + file.getName();
                Image image = new Image(file.toURI().toString());

                int frameCount = (int) (image.getWidth() / frameWidth);
                AnimationChannel channel = new AnimationChannel(image, frameCount, frameWidth, frameHeight, duration);
                String animName = file.getName().replace(".png", "");
                animations.put(animName, channel);

                LOGGER.info("Loaded animation: " + animName);
            }
        }

        return animations;
    }

    private static Image createPlaceholderImage(String text, int width, int height) {
        javafx.scene.canvas.Canvas canvas = new javafx.scene.canvas.Canvas(width, height);
        var gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, width, height);

        gc.setFill(Color.RED);
        gc.setFont(new Font(14));
        gc.fillText(text, 10, height / 2.0);

        return canvas.snapshot(null, null);
    }

    public static AnimatedTexture createAnimatedTexture(AnimationChannel channel) {
        AnimatedTexture texture = new AnimatedTexture(channel);
        texture.loop();
        return texture;
    }
}
