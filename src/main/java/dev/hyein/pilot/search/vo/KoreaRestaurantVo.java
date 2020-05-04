package dev.hyein.pilot.search.vo;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class KoreaRestaurantVo {
    @CsvBindByPosition(position = 0)
    private String title;
    @CsvBindByPosition(position = 1)
    private String category1;
    @CsvBindByPosition(position = 2)
    private String category2;
    @CsvBindByPosition(position = 3)
    private String category3;
    @CsvBindByPosition(position = 4)
    private String region;
    @CsvBindByPosition(position = 5)
    private String city;
    @CsvBindByPosition(position = 6)
    private String description;

}
