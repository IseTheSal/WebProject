package by.learning.web.model.entity;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.Set;

public class Game {

    private int id;
    private String title;
    private String description;
    private String imagePath;
    private BigDecimal price;
    private String trailer;
    private Set<GameGenre> genres = EnumSet.noneOf(GameGenre.class);
    private Set<GameCategory> categories = EnumSet.noneOf(GameCategory.class);

    public enum GameGenre {
        ACTION,
        ADVENTURE,
        ARCADE,
        CASUAL,
        HORROR,
        INDIE,
        OPEN_WORLD,
        RACING,
        RPG,
        SHOOTERS,
        SIMULATORS,
        SPORT,
        STRATEGIES,
        SURVIVAL,
        VR;

        private static final String DELIMITER = ",";

        private static Set<GameGenre> convertFromString(String genreString) {
            Set<GameGenre> result = EnumSet.noneOf(GameGenre.class);
            String[] genreArray = genreString.split(DELIMITER);
            for (String genre : genreArray) {
                GameGenre gameGenre = GameGenre.valueOf(genre);
                result.add(gameGenre);
            }
            return result;
        }
    }

    public enum GameCategory {
        SINGLEPLAYER,
        MULTIPLAYER,
        COOPERATIVE,
        ONLINE_COOP,
        CONTROLLER_FRIENDLY;

        private static final String DELIMITER = ",";

        private static Set<GameCategory> convertFromString(String categoryString) {
            Set<GameCategory> result = EnumSet.noneOf(GameCategory.class);
            String[] categoryArray = categoryString.split(DELIMITER);
            for (String category : categoryArray) {
                GameCategory gameCategory = GameCategory.valueOf(category);
                result.add(gameCategory);
            }
            return result;
        }
    }

    public Game(int id) {
        this.id = id;
    }

    public Game(int id, String title, String imagePath) {
        this.id = id;
        this.title = title;
        this.imagePath = imagePath;
    }

    public Game(String title, String imagePath, String description, BigDecimal price, String trailer) {
        this.title = title;
        this.imagePath = imagePath;
        this.description = description;
        this.price = price;
        this.trailer = trailer;
    }

    public Game(int id, String title, String description, String imagePath, BigDecimal price, String trailer, String genreString, String categoryString) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.price = price;
        this.trailer = trailer;
        this.genres = GameGenre.convertFromString(genreString);
        this.categories = GameCategory.convertFromString(categoryString);
    }

    public Game(int id, String title, String imagePath, BigDecimal price, String genreString, String categoryString) {
        this.id = id;
        this.title = title;
        this.imagePath = imagePath;
        this.price = price;
        this.genres = GameGenre.convertFromString(genreString);
        this.categories = GameCategory.convertFromString(categoryString);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Set<GameGenre> getGenres() {
        return Set.copyOf(genres);
    }

    public void setGenres(EnumSet<GameGenre> genres) {
        this.genres = genres;
    }

    public boolean addGenre(GameGenre genre) {
        return genres.add(genre);
    }

    public Set<GameCategory> getCategories() {
        return Set.copyOf(categories);
    }

    public void setCategories(EnumSet<GameCategory> categories) {
        this.categories = categories;
    }

    public boolean addCategory(GameCategory category) {
        return categories.add(category);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (id != game.id) return false;
        if (title != null ? !title.equals(game.title) : game.title != null) return false;
        if (description != null ? !description.equals(game.description) : game.description != null) return false;
        if (imagePath != null ? !imagePath.equals(game.imagePath) : game.imagePath != null) return false;
        if (price != null ? !price.equals(game.price) : game.price != null) return false;
        if (trailer != null ? !trailer.equals(game.trailer) : game.trailer != null) return false;
        if (genres != null ? !genres.equals(game.genres) : game.genres != null) return false;
        return categories != null ? categories.equals(game.categories) : game.categories == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (imagePath != null ? imagePath.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (trailer != null ? trailer.hashCode() : 0);
        result = 31 * result + (genres != null ? genres.hashCode() : 0);
        result = 31 * result + (categories != null ? categories.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Game{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", imagePath='").append(imagePath).append('\'');
        sb.append(", price=").append(price);
        sb.append(", trailer='").append(trailer).append('\'');
        sb.append(", genres=").append(genres);
        sb.append(", categories=").append(categories);
        sb.append('}');
        return sb.toString();
    }
}
