package com.trisvc.modules.parser.stanford;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreePrint;
import edu.stanford.nlp.trees.TreebankLanguagePack;

public class Example {
	 public static void main(String[] args) {
			System.out.println("dentro");
		    String parserModel = "edu/stanford/nlp/models/lexparser/spanishPCFG.ser.gz";
		    if (args.length > 0) {
		      parserModel = args[0];
		    }
		    LexicalizedParser lp = LexicalizedParser.loadModel(parserModel);

		    if (args.length == 0) {
		      demoAPI(lp);
		    } else {
		      String textFile = (args.length > 1) ? args[1] : args[0];
		      demoDP(lp, textFile);
		    }
		  }

		  /**
		   * demoDP demonstrates turning a file into tokens and then parse
		   * trees.  Note that the trees are printed by calling pennPrint on
		   * the Tree object.  It is also possible to pass a PrintWriter to
		   * pennPrint if you want to capture the output.
		   * This code will work with any supported language.
		   */
		  public static void demoDP(LexicalizedParser lp, String filename) {
		    // This option shows loading, sentence-segmenting and tokenizing
		    // a file using DocumentPreprocessor.
		    TreebankLanguagePack tlp = lp.treebankLanguagePack(); // a PennTreebankLanguagePack for English
		    GrammaticalStructureFactory gsf = null;
		    if (tlp.supportsGrammaticalStructures()) {
		      gsf = tlp.grammaticalStructureFactory();
		    }
		    // You could also create a tokenizer here (as below) and pass it
		    // to DocumentPreprocessor
		    for (List<HasWord> sentence : new DocumentPreprocessor(filename)) {
		      Tree parse = lp.apply(sentence);
		      parse.pennPrint();
		      System.out.println();

		      if (gsf != null) {
		        GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
		        Collection tdl = gs.typedDependenciesCCprocessed();
		        System.out.println(tdl);
		        System.out.println();
		      }
		    }
		  }

		  /**
		   * demoAPI demonstrates other ways of calling the parser with
		   * already tokenized text, or in some cases, raw text that needs to
		   * be tokenized as a single sentence.  Output is handled with a
		   * TreePrint object.  Note that the options used when creating the
		   * TreePrint can determine what results to print out.  Once again,
		   * one can capture the output by passing a PrintWriter to
		   * TreePrint.printTree. This code is for English.
		   */
		  public static void demoAPI(LexicalizedParser lp) {
		    // This option shows parsing a list of correctly tokenized words
		    /*String[] sent = { "This", "is", "an", "easy", "sentence", "." };
		    List<CoreLabel> rawWords = Sentence.toCoreLabelList(sent);
		    Tree parse = lp.apply(rawWords);
		    parse.pennPrint();
		    System.out.println();*/

		    // This option shows loading and using an explicit tokenizer
		    //String sent2 = "enciende la luz de la habitación pequeña";
		    //String sent2 = "avísame pasado mañana a las 3 de la tarde";
		    String sent2 = "avísame  mañana a las 3";
		    TokenizerFactory<CoreLabel> tokenizerFactory =
		        PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
		    Tokenizer<CoreLabel> tok =
		        tokenizerFactory.getTokenizer(new StringReader(sent2));
		    List<CoreLabel> rawWords2 = tok.tokenize();
		    Tree parse = lp.apply(rawWords2);
		//parse.pennPrint();

		   /* TreebankLanguagePack tlp = lp.treebankLanguagePack(); // PennTreebankLanguagePack for English
		    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
		    GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
		    List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
		    System.out.println(tdl);
		    System.out.println();*/

		    // You can also use a TreePrint object to print trees and dependencies
		//System.out.println("jor");
		/*
		 * penn, oneline, rootSymbolOnly, words, wordsAndTags, 
		 * dependencies, typedDependencies, typedDependenciesCollapsed, 
		 * latexTree, xmlTree, collocations, semanticGraph, 
		 * conllStyleDependencies, conll2007
		 * 
		 * */
		    
		    TreePrint tp2 = new TreePrint("xmlTree");
		    tp2.printTree(parse);
		    List<StringBuffer> frases = new ArrayList<StringBuffer>();
		    frases.add(new StringBuffer(""));
		    imprimir (parse,frases);
		    
		    for (StringBuffer s: frases){
		    	System.out.println(s);
		    }
	    
		  }
		  
		  public static void imprimir (Tree p, List<StringBuffer> frases){
			  	String value = p.value();
			  	StringBuffer acumulador = frases.get(frases.size()-1);
			  	if ("s.axxx".equals(value)||"sp".equals(value)||"grup.verb".equals(value)){
			  		frases.add(new StringBuffer(""));
			  	}
			  	if (p.isLeaf()){
			  		acumulador.append(" "+value); 
			  	}
			  	//System.out.println(value+" "+p.isLeaf()+" "+acumulador);
			    for (Tree t: p.children()){
			    	imprimir(t,frases);
			    }	
			  	if ("grup.verb".equals(value)){
			  		frases.add(new StringBuffer(""));
			  	}			    
		  }


		}
