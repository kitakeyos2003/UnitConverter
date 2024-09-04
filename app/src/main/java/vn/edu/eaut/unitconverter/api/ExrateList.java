package vn.edu.eaut.unitconverter.api;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "ExrateList", strict = false)
public class ExrateList {

    @Element(name = "DateTime")
    private String dateTime;

    @ElementList(inline = true)
    private List<Exrate> exrates;

    @Element(name = "Source")
    private String source;

    public ExrateList() {
        this.exrates = new ArrayList<>();
    }

    public void addExrate(Exrate exrate) {
        this.exrates.add(exrate);
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<Exrate> getExrates() {
        return exrates;
    }

    public void setExrates(List<Exrate> exrates) {
        this.exrates = exrates;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}