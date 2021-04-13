package by.learning.web.model.entity;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;

/**
 * Game entity.
 *
 * @author Illia Aheyeu
 */
public class Game {

    private final int id;
    private final String title;
    private final String description;
    private final String imagePath;
    private final BigDecimal price;
    private final String trailer;
    private final Set<Genre> genres;
    private final Set<Category> categories;

    /**
     * Game genres enum.
     *
     * @author Illia Aheyeu
     * @see Game
     */
    public enum Genre {
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

        /**
         * Allows convert genres from <code>String</code>  which divided by delimiter
         *
         * @param genreString Genres string
         * @return <code>Set</code> of genres
         */
        private static Set<Genre> convertFromString(String genreString) {
            Set<Genre> result = EnumSet.noneOf(Genre.class);
            String[] genreArray = genreString.split(DELIMITER);
            for (String genre : genreArray) {
                Genre gameGenre = Genre.valueOf(genre.toUpperCase(Locale.ROOT));
                result.add(gameGenre);
            }
            return result;
        }
    }

    /**
     * Game categories enum.
     *
     * @author Illia Aheyeu
     * @see Game
     */
    public enum Category {
        SINGLEPLAYER,
        MULTIPLAYER,
        COOPERATIVE,
        ONLINE_COOP,
        CONTROLLER_FRIENDLY;

        private static final String DELIMITER = ",";

        /**
         * Allows convert categories from <code>String</code> which divided by delimiter
         *
         * @param categoryString Genres string
         * @return <code>Set</code> of categories
         */
        private static Set<Category> convertFromString(String categoryString) {
            Set<Category> result = EnumSet.noneOf(Category.class);
            if (categoryString == null || categoryString.isBlank()) {
                return result;
            }
            String[] categoryArray = categoryString.split(DELIMITER);
            for (String category : categoryArray) {
                Category gameCategory = Category.valueOf(category.toUpperCase(Locale.ROOT));
                result.add(gameCategory);
            }
            return result;
        }
    }

    private Game(GameBuilder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.imagePath = builder.imagePath;
        this.price = builder.price;
        this.trailer = builder.trailer;
        this.genres = builder.genres;
        this.categories = builder.categories;
    }

    /**
     * Create game with Builder pattern.
     *
     * @author Illia Aheyeu
     * @see Game
     */
    public static class GameBuilder {
        private int id;
        private final String title;
        private String description;
        private final String imagePath;
        private BigDecimal price;
        private String trailer;
        private Set<Genre> genres = EnumSet.noneOf(Genre.class);
        private Set<Category> categories = EnumSet.noneOf(Category.class);

        public GameBuilder(String title, String imagePath) {
            this.title = title;
            this.imagePath = imagePath;
        }

        public GameBuilder id(int id) {
            this.id = id;
            return this;
        }

        public GameBuilder description(String description) {
            this.description = description;
            return this;
        }

        public GameBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public GameBuilder trailer(String trailer) {
            this.trailer = trailer;
            return this;
        }

        public GameBuilder genres(String genres) {
            this.genres = Genre.convertFromString(genres);
            return this;
        }

        public GameBuilder categories(String categories) {
            this.categories = Category.convertFromString(categories);
            return this;
        }

        public Game build() {
            return new Game(this);
        }
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Set<Genre> getGenres() {
        return Set.copyOf(genres);
    }

    public Set<Category> getCategories() {
        return Set.copyOf(categories);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getTrailer() {
        return trailer;
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
