    package com.example.DoAnWebJava.dto;

    import com.example.DoAnWebJava.support.CommonAbstractDto;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import java.util.Date;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class SupplierDto extends CommonAbstractDto {
        private int id;
        private String title;
        private String description;
        private String image;
        private boolean isActivate;

        public SupplierDto(int id, String title, String description, String image, boolean isActivate, Date createdDate, Date modifiedDate) {
            super(createdDate, modifiedDate);
            this.id = id;
            this.title = title;
            this.description = description;
            this.image = image;
            this.isActivate = isActivate;
        }
    }
