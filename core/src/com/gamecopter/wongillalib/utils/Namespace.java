package com.gamecopter.wongillalib.utils;

import java.util.ArrayList;

/**
 * Created by Kevin Wong on 7/2/2014.
 */
public class Namespace {
    protected String namespace;
    private ArrayList<ElementDirective> DirectiveElements = new ArrayList<ElementDirective>();
    private ArrayList<AttributeDirective> CommonAttributes = new ArrayList<AttributeDirective>();

    public Namespace(String namespace) {
        this.namespace = namespace;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public void addElementDirectives(ArrayList<ElementDirective> DirectiveElements) {
        this.DirectiveElements.addAll(DirectiveElements);
    }

    public void addCommonAttributes(ArrayList<AttributeDirective> CommonAttributes) {
        this.CommonAttributes.addAll(CommonAttributes);
    }


    public ArrayList<AttributeDirective> getCommonAttributes() {
        return CommonAttributes;
    }

    public ArrayList<ElementDirective> getDirectiveElements() {
        return DirectiveElements;
    }
}
