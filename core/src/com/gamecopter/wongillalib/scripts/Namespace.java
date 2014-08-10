package com.gamecopter.wongillalib.scripts;


import java.util.ArrayList;

/**
 * Created by Kevin Wong on 8/6/2014.
 */
public class Namespace {
    private Namespace parent;
    private ArrayList<Namespace> childs;
    private String name;
    private ArrayList<ElementDirective> elements;
    private ArrayList<AttributeDirective> attributes;

    private Namespace(String name)
    {
        this.name = name;
    }

    public Namespace()
    {
    }

    public Namespace namespace(String name)
    {
        String[] names = name.split(".");

        Namespace ns = this;

        for(String n:names) {
            ns = createNamespace(name, ns);
        }

        return ns;
    }

    public static Namespace createRootNamespace(String singleName)
    {
        return createNamespace(singleName, null);
    }

    protected static Namespace createNamespace(String singleName, Namespace parentNamespace)
    {
        if(singleName.contains("."))
            return null;

        Namespace p = new Namespace(singleName);

        p.setParent(parentNamespace);

        if(parentNamespace!=null)
           parentNamespace.addChild(p);

        return p;
    }

    public Namespace getParent() {
        return parent;
    }

    public boolean containsChild(String namespace)
    {
        for(Namespace ns: childs)
        {
            if(ns.getName().equalsIgnoreCase(namespace))
            {
                return true;
            }
        }

        return false;
    }

    protected void setParent(Namespace parent) {
        this.parent = parent;
    }

    public ArrayList<Namespace>  getChild() {
        return childs;
    }

    public void addChild(Namespace child) {

        if(childs==null)
            childs = new ArrayList<Namespace>();

        //ToDO: no duplicate namespace is allowed.
        if(!childs.contains(child) && !this.containsChild(child.getName()))
            childs.add(child);
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        Namespace ns = this;
        String fullName = "";

        do {
            if(fullName.length() > 0 )
                fullName += ".";

            fullName += ns.getName();
            ns = ns.getParent();
        }while(ns !=null);

        return fullName;
    }

    public void add(ElementDirective d)
    {
            if (elements == null)
                elements = new ArrayList<ElementDirective>();

            elements.add(d);
    }

    public void add(AttributeDirective d)
    {
            if (attributes == null)
                attributes = new ArrayList<AttributeDirective>();

            attributes.add(d);
    }

}
