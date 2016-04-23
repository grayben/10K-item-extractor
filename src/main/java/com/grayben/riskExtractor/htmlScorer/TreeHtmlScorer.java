package com.grayben.riskExtractor.htmlScorer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeTraversor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ListIterator;

/**
 * Parses HTML into a tree structure before processing its contents.
 * <p>
 * Written by Ben Gray, 2015.
 */
public class TreeHtmlScorer implements HtmlScorer {

    /**
     * The node traversor to use to traverse the generated tree structure.
     */
	NodeTraversor nt;

    /**
     * The scoring and flattening node visitor to pass into {@link #nt}.
     */
    ScoringAndFlatteningNodeVisitor nv;

    /**
     * Instantiate relying upon the specified node visitor.
     *
     * @param nv the node visitor upon which to rely to score elements of the HTML tree
     */
	//TODO: refactor ScoringAndFlatteningNodeVisitor into intermediate abstraction
	public TreeHtmlScorer(ScoringAndFlatteningNodeVisitor nv) {
		super();
		this.nv = nv;
		this.nt = new NodeTraversor(this.nv);
	}

    /**
     * Traverse the specified <b>HTML tree</b> with the {@link ScoringAndFlatteningNodeVisitor} specified
     * at construction, producing scored text.
     * @param node the root of the HTML tree
     * @return the text, decorated with scores
     * @throws NullPointerException
     */
	private ScoredText traverse(Node node)
			throws NullPointerException {
		if(node != null){
			nt.traverse(node);
		} else {
			throw new NullPointerException();
		}
		return nv.getFlatText();
	}


	public ScoredText scoreHtml(InputStream inputStream, String charsetName, String baseUri) throws IOException {
		if (inputStream == null) {
			throw new NullPointerException("inputStream was null");
		}
		if (charsetName == null) {
			throw new NullPointerException("charsetName was null");
		}
		if (baseUri == null) {
			throw new NullPointerException("baseUri was null");
		}
		Document doc = parseHtmlFileFromInputStream(inputStream, charsetName, baseUri);
		Element body = getHtmlBody(doc);
		return traverse(body);
	}

    /**
     * Extract only the HTML enclosed by body tags.
     *
     * @param doc the HTML document form which to extract the body
     * @return the root of the tree extracted from within the body tags
     */
	private Element getHtmlBody(Document doc) {
		Elements elements = doc.select("html > body");
		ListIterator<Element> iterator = elements.listIterator();
		int i = 1;
		while (iterator.hasNext()){
			System.out.println(i++);
			System.out.println(iterator.next().html());	
		}

		//TODO: implement
		return null;
	}

	@Override
	public ScoredText scoreHtml(String url) throws IOException {
		//TODO: use JSoup to get a Connection.Response so can remove SEC head
		Document doc = parseHtmlUrl(url);
		return traverse(doc);
	}

    /**
     * @param url the URL of the HTML to parse
     * @return the HTML parsed as a tree
     * @throws IOException
     */
	private Document parseHtmlUrl(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		return doc;
	}

    /**
     * @param inputStream the input stream representing the html
     * @param charsetName the name of the charset used to encode the file
	 * @param baseUri the baseURI of the html
     * @return the HTML parsed as a tree
     */
	private Document parseHtmlFileFromInputStream(InputStream inputStream, String charsetName, String baseUri) throws IOException {
		Document doc = Jsoup.parse(inputStream, charsetName, baseUri);
		return doc;
	}
}
