package gwtquery.test.testissue302.client;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.body;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import java.util.ArrayList;
import java.util.List;

public class TestIssue302 implements EntryPoint {

    public static final String CUSTOM_SELECTOR = "[justAsToken]";
    public static final String STANDARD_SELECTOR = "[__gwt_cell]";

    public void onModuleLoad() {
        RootPanel.get();
        // live events performance
        final Label breadcrumb = new Label();
        final ListBox algorithm = new ListBox();
        algorithm.addItem("custom selector");
        algorithm.addItem("standard selector");
        algorithm.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                String value = algorithm.getSelectedValue();
                $(body).as(com.google.gwt.query.client.plugins.Events.class).off();
                if (value.startsWith("custom")) {
                    bindSelectors(CUSTOM_SELECTOR);
                } else {
                    bindSelectors(STANDARD_SELECTOR);
                }
            }
        });

        bindSelectors(CUSTOM_SELECTOR);

        final AbstractCellTable<String> bigTable = new CellTable<String>(Integer.MAX_VALUE);
        final TextColumn<String> col = new TextColumn<String>() {
            @Override
            public String getValue(String object) {
                return object;
            }
        };

        final IntegerBox cols = new IntegerBox();
        cols.setTitle("cols");
        cols.addValueChangeHandler(new ValueChangeHandler<Integer>() {
            @Override
            public void onValueChange(ValueChangeEvent<Integer> event) {
                while (bigTable.getColumnCount() > 0) bigTable.removeColumn(0);
                for (int i = 0; i < event.getValue(); i++) {
                    bigTable.addColumn(col);
                }
            }
        });
        cols.setValue(10, true);

        IntegerBox rows = new IntegerBox();
        rows.setTitle("rows");
        rows.addValueChangeHandler(new ValueChangeHandler<Integer>() {
            @Override
            public void onValueChange(ValueChangeEvent<Integer> event) {
                List<String> listData = new ArrayList<String>(event.getValue());
                for (int i = 0; i < event.getValue(); i++) {
                    listData.add("#" + (int) (Math.random() * 1000));
                }
                bigTable.setRowData(listData);
            }
        });
        rows.setValue(1000, true);

        ScrollPanel vScroll = new ScrollPanel(bigTable);
        vScroll.setWidth("400px");
        vScroll.setHeight("300px");

        FlowPanel widgets = new FlowPanel();
        widgets.add(new InlineLabel("algorithm: "));
        widgets.add(algorithm);
        widgets.add(new InlineLabel(" cols: "));
        widgets.add(cols);
        widgets.add(new InlineLabel(" rows: "));
        widgets.add(rows);
        widgets.add(breadcrumb);
        widgets.add(vScroll);

        RootPanel.get().add(widgets);
    }

    private void bindSelectors(String selector) {
        $(body).on("mouseenter", selector, new Function() {
            @Override
            public void f(final Element e) {
                $(e).css("color", "red");
            }
        });
        $(body).on("mouseleave", selector, new Function() {
            @Override
            public void f(final Element e) {
                $(e).css("color", null);
            }
        });
    }

}
