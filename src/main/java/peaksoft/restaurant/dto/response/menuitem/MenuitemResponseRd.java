    package peaksoft.restaurant.dto.response.menuitem;


    public record MenuitemResponseRd(
            Long id,
            String name,
            String image,
            double price,
            String description,
            boolean isVegetarian
    ) {
    }
