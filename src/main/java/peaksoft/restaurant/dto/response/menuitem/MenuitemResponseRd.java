    package peaksoft.restaurant.dto.response.menuitem;


    public record MenuitemResponseRd(
            Long id,
            String name,
            String image,
            String description,
            double price,
            boolean isVegetarian
    ) {
    }
