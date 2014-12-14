package gwtquery.test.testissue302.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.query.client.Predicate;
import com.google.gwt.query.client.impl.SelectorEngine;

public class TestIssue302SelectorEngine extends SelectorEngine {
    @Override
    public NodeList<Element> filter(NodeList<Element> nodes, String selector, boolean filterDetached) {
        if (selector.equals(TestIssue302.CUSTOM_SELECTOR)) {
            GWT.log("using custom selector");
            return filter(nodes, new Predicate() {
                @Override
                public boolean f(Element e, int index) {
                    return e.hasAttribute("__gwt_cell");
                }
            });
        }
        GWT.log("using standard selector");
        return super.filter(nodes, selector, filterDetached);
    }
}
