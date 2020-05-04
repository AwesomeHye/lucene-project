package dev.hyein.pilot.search.helper;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

/**
 * require: opencsv library
 */
public class CsvLoader {

    /**
     * input: csv file path
     * output: vos
     * @param pojo
     * @param <T>
     * @return
     */
    public static <T> List<T> readCsv(T pojo, String filePath) throws FileNotFoundException {
        FileReader fileReader = new FileReader(filePath);
        CsvToBean csvToBean = getSimpleCsvToBeanBuild(fileReader, pojo);
        List<T> pojos = csvToBean.parse();
        return pojos;
    }

    private static <T> CsvToBean getSimpleCsvToBeanBuild(Reader reader, T pojo){
        return new CsvToBeanBuilder(reader)
                .withType(pojo.getClass())
                //.withSkipLines(1) //제목행 건너뛰기
                .build();
    }
}
