package com.gamecopter.wongillalib.scripts;


import java.util.ArrayList;
import java.util.List;

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
        String[] names = name.split("\\.");

        Namespace parent = this;

            for (String tmpName : names) {
                parent = createNamespace(tmpName, parent);
            }

        return parent;
    }

    /**
     *
     * @param singleName the name of Root namespace.
     * @return the root Namespace without a parent
     */
    public static Namespace createRootNamespace(String singleName)
    {

        return createNamespace(singleName, null);
    }

    protected static Namespace createNamespace(String singleName, Namespace parentNamespace)
    {
        if(singleName.contains("."))
            return null;

        if(parentNamespace!=null) {
            ArrayList<Namespace> namespaces = parentNamespace.getChilds();

            if(namespaces!=null) {
                for (Namespace ns : namespaces) {
                    if (ns.getName().equalsIgnoreCase(singleName))
                        return ns;
                }
            }
        }

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

    public ArrayList<Namespace> getChilds() {
        return childs;
    }

    public void addChild(Namespace child) {

        if(child==null) // no child is available
            return;

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

            String name = ns.getName();

            if(fullName.length() > 0 )
                fullName = "." + fullName;

            if(name !=null )
               fullName = name  + fullName;

            ns = ns.getParent();

        }while(ns !=null);

        return fullName;
    }

    public boolean isNamespaceListEmpty()
    {
        return (childs == null || (childs.size()==0) );
    }

    public boolean isElementListEmpty()
    {
        return (elements == null || (elements.size()==0) );
    }

    public boolean isAttributeListEmpty()
    {
        return (attributes == null || (attributes.size()==0) );
    }

    public boolean isDirectiveAvailable()
    {
        return isElementListEmpty() && isAttributeListEmpty();
    }

    public void addElement(ElementDirective d)
    {
            if (elements == null)
                elements = new ArrayList<ElementDirective>();

            elements.add(d);
    }

    public void addAttribute(AttributeDirective d)
    {
        if (attributes == null)
            attributes = new ArrayList<AttributeDirective>();

        attributes.add(d);
    }

    public void addElements(List<ElementDirective> ds)
    {
        if (elements == null)
            elements = new ArrayList<ElementDirective>();

        elements.addAll(ds);
    }

    public void addAttributes(List<AttributeDirective> ds)
    {
            if (attributes == null)
                attributes = new ArrayList<AttributeDirective>();

            attributes.addAll(ds);
    }

    public ArrayList<ElementDirective> getElements() {
        return elements;
    }

    public ArrayList<AttributeDirective> getAttributes() {
        return attributes;
    }
}
