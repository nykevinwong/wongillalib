package com.gamecopter.wongillalib.utils;

/**
 * Created by Kevin Wong on 6/24/2014.
 */
public class AttributeDirective extends ElementDirective {

    public AttributeDirective(String directiveName) {
        super(directiveName);

        this.directiveType = RestrictType.Attribute;
    }
}
