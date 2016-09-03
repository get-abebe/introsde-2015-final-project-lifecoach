package ehealth.external.forismatic;


/**
 * ForismaticQuote java object class that holds the values of ForismaticQuote Web object. 
 * 
 * @author getch
 *
 */
public class ForismaticQuote{

	public String quoteText;
	public String quoteAuther;
	public String quoteLink;
	
	/**
	 * 
	 */
	public ForismaticQuote() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * 
	 * @param quoteText
	 * @param quoteAuther
	 * @param quoteLink
	 * 
	 * @return
	 */
	public ForismaticQuote(String quoteText, String quoteAuther, String quoteLink) {
		this.quoteText = quoteText;
		this.quoteLink = quoteLink;
		this.quoteAuther = quoteAuther;
	}
}
