/**
   Copyright (c) 2009, the SemanticVectors AUTHORS.

   All rights reserved.

   Redistribution and use in source and binary forms, with or without
   modification, are permitted provided that the following conditions are
   met:

 * Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.

 * Redistributions in binary form must reproduce the above
   copyright notice, this list of conditions and the following
   disclaimer in the documentation and/or other materials provided
   with the distribution.

 * Neither the name of the University of Pittsburgh nor the names
   of its contributors may be used to endorse or promote products
   derived from this software without specific prior written
   permission.

   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
   "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
   LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
   A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
   CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
   EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
   PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
   PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
   LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
   NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 **/

package pitt.search.semanticvectors;

import java.lang.IllegalArgumentException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Class for representing and parsing global command line flags.
 *
 * All command line flags for the SemanticVectors package should be defined here.
 * This design is a violation of encapsulation, but since these are things that
 * the user can break, we believe that we'll create a much cleaner package if we
 * put this power into user's hands explicitly, but at least insist that all command
 * line flags are declared in one place - in the Flags class. Needless to say, the
 * Flags class only looks after the basic syntax of (name, value) command line flags.
 * All semantics (i.e., in this case, behaviour affected by the flags) is up to the
 * developer to implement.
 *
 * @author Dominic Widdows
 */
public class FlagConfig {
  private static final Logger logger = Logger.getLogger(FlagConfig.class.getCanonicalName());
  
  public String[] remainingArgs;

  // Add new command line flags here. By convention, please use lower case.
  //
  // DO NOT DUPLICATE NAMES HERE! YOU WILL OVERWRITE OTHER PEOPLE's FLAGS!
  private int dimension = 200;
  public int getDimension() { return dimension; }
  public void setDimension(int dimension) { this.dimension = dimension; }
  public static final String dimensionDescription = "Dimension of semantic vector space";

  private String vectortype = "real";
  public String getVectortype() { return vectortype; }
  public void setVectortype(String vectortype) {this.vectortype = vectortype; }
  public static final String vectortypeDescription = "Ground field for vectors: real, binary or complex.";
  public static final String[] vectortypeValues = {"binary", "real", "complex"};

  private int seedlength = 10;
  public int getSeedlength() { return seedlength; }
  public static final String seedlengthDescription =
    "Number of +1 and number of -1 entries in a sparse random vector";

  private int binaryvectordecimalplaces = 2;
  public int getBinaryvectordecimalplaces() { return binaryvectordecimalplaces; }
  public static final String binaryvectordecimalplacesDescription =
    "Number of decimal places to consider in weighted superpositions of binary vectors. Higher precision requires additional memory during training.";
  
  private int minfrequency = 0;
  public int getMinfrequency() { return minfrequency; }
  private int maxfrequency = Integer.MAX_VALUE;
  public int getMaxfrequency() { return maxfrequency; }
  private int maxnonalphabetchars = Integer.MAX_VALUE;
  public int getMaxnonalphabetchars() { return maxnonalphabetchars; }
  
  private String indexrootdirectory = "";
  public String getIndexrootdirectory() { return indexrootdirectory; }
  public String indexRootDirectoryDescription = "Allow for the specification of a directory to place the lucene index in. Requires a trailing slash";
  
  private int numsearchresults = 20;
  public int getNumsearchresults() { return numsearchresults; }
  public double searchresultsminscore = -1.0;
  public static final String searchresultsminscoreDescription = "Search results with similarity scores below "
    + "this value will not be included in search results.";

  private int numclusters = 5;
  public int getNumclusters() { return numclusters; }
  private int trainingcycles = 0;
  public int getTrainingcycles() { return trainingcycles; }
  private int windowradius = 5;
  public int getWindowradius() { return windowradius; }

  private String searchtype = "sum";
  public String getSearchtype() { return searchtype; }
  public static final String searchtypeDescription = "Method used for combining and searching vectors.";
  public static final String[] searchtypeValues =
    {"sum", "sparsesum", "subspace", "maxsim", "balanced_permutation", "permutation",
     "boundproduct", "boundproductsubspace", "analogy", "printquery"};

  private boolean fieldweight = false;
  public boolean getFieldweight() { return fieldweight; }
  public static final String fieldweightDescription =
	  "Set to true if you want document vectors built from multiple fields to emphasize terms from shorter fields";
  
  private String termweight = "none";
  public String getTermweight() { return termweight; }
  public static final String termweightDescription = "Term weighting used when constructing document vectors.";
  public static final String[] termweightValues = {"logentropy","idf", "none"};

  private boolean porterstemmer = false;
  public boolean getPorterStemmer() { return porterstemmer; }
  public static final String porterstemmerDescription =
    "Set to true when using IndexFilePositions if you would like to stem terms";

  private boolean usetermweightsinsearch = false;
  public boolean getUsetermweightsinsearch() { return usetermweightsinsearch; }
  public static final String usetermweightsinsearchDescription =
    "Set to true only if you want to scale each comparison score by a term weight during search.";

  private boolean stdev = false;
  public boolean getStdev() { return stdev; }
  public static final String stdevDescription =
    "Set to true when you would prefer results scored as SD above the mean across all search vectors";

  private boolean expandsearchspace = false;
  public boolean getExpandsearchspace() { return expandsearchspace; }
  public static final String expandsearchspaceDescription =
	  "Set to true to generated bound products from each pairwise element of the search space. "+
	  "Expands the size of the space to n-squared";
  
  private String indexfileformat = "lucene";
  public String getIndexfileformat() { return indexfileformat; }
  public static final String indexfileformatDescription =
    "Format used for serializing / deserializing vectors from disk";
  public static final String[] indexfileformatValues = {"lucene", "text"};

  private String termvectorsfile = "termvectors";
  public String getTermvectorsfile() { return termvectorsfile; }
  private String docvectorsfile = "docvectors";
  public String getDocvectorsfile() { return docvectorsfile; }
  private String termtermvectorsfile = "termtermvectors";
  public String getTermtermvectorsfile() { return termtermvectorsfile; }
  
  private String queryvectorfile = "termvectors";
  public String getQueryvectorfile() { return queryvectorfile; }
  public static String queryvectorfileDescription = "Principal vector store for finding query vectors.";

  private String searchvectorfile = "";
  public String getSearchvectorfile() { return searchvectorfile; }
  public static String searchvectorfileDescription =
      "Vector store for searching. Defaults to being the same as {@link #queryVecReader}. "
      + "May be different from queryvectorfile e.g., when using terms to search for documents.";
  
  private String boundvectorfile = "";
  public String getBoundvectorfile() { return boundvectorfile; }
  public static String boundvectorfileDescription =
      "Auxiliary vector store used when searching for boundproducts. Used only in some searchtypes.";

  // Got to here ... fix up later if it works
  public String elementalvectorfile = "elementalvectors";
  public static String elementalvectorfileDescription =
      "Random elemental vectors, sometimes written out, and used (e.g.) in conjunction with permuted vector file.";
  
  public String semanticvectorfile = "semanticvectors";
  public static String semanticvectorfileDescription = "Semantic vectors; used so far as a name in PSI.";

  public String predicatevectorfile = "predicatevectors";
  public static String predicatevectorfileDescription = "Vectors used to represent predicates in PSI.";
  
  public String permutedvectorfile = "permtermvectors";
  public static String permutedvectorfileDescription =
      "Permuted term vectors, output by -positionalmethod permutation.";
  
  public String directionalvectorfile ="drxntermvectors";
  public static String directionalvectorfileDescription =
      "Permuted term vectors, output by -positionalmethod directional";
  
  public String permplustermvectorfile ="permplustermvectors";
  public static String permplustermvectorfileDescription =
      "Permuted term vectors, output by -positionalmethod permutation_plus_basic";
  
  public String positionalmethod = "basic";
  public static String positionalmethodDescription = "Method used for positional indexing.";
  public static String positionalmethodValues[] =
      {"basic", "directional", "permutation","permutation_plus_basic"};
  
  public String stoplistfile = "";
  public String startlistfile = "";
  public String luceneindexpath = "";
  public String initialtermvectors = "";
  public static String initialtermvectorsDescription =
    "Use the vectors in this file for initialization instead of new random vectors.";

  public String initialdocumentvectors = "";
  public static String initialdocumentvectorsDescription =
    "Use the vectors in this file for initialization instead of new random vectors.";

  public String docindexing = "inmemory";
  public static String docindexingDescription = "Memory management method used for indexing documents.";
  public static String docindexingValues[] = {"inmemory", "incremental", "none"};

  public String vectorlookupsyntax = "exactmatch";
  public static final String vectorlookupsyntaxDescription =
    "Method used for looking up vectors in a vector store";
  public static String[] vectorlookupsyntaxValues = {"exactmatch", "regex"};

  public boolean matchcase = false;

  public String vectorstorelocation = "ram";
  public static String vectorstorelocationDescription = "Where to store vectors - in memory or on disk";
  public static String[] vectorstorelocationValues = {"ram", "disk"};

  public String batchcompareseparator = "\\|";
  public static String batchcompareseparatorDescription = "Separator for documents on a single line in batch comparison mode.";

  public boolean suppressnegatedqueries = false;
  public static String suppressnegatedqueriesDescription = "Suppress checking for the query negation token which indicates subsequent terms are to be negated when comparing terms. If this is set all terms are treated as positive";

  public String[] contentsfields = {"contents"};
  public static String docidfield = "path";

  /**
   * Parse flags from a single string.  Presumes that string contains only command line flags.
   */
  public FlagConfig parseFlagsFromString(String header) {
    String[] args = header.split("\\s");
    return new FlagConfig(args);
  }

  /**
   * Parse command line flags and create public data structures for accessing them.
   * @param args
   * @return trimmed list of arguments with command line flags consumed
   */
  // This implementation is linear in the number of flags available
  // and the number of command line arguments given. This is quadratic
  // and so inefficient, but in practice we only have to do it once
  // per command so it's probably negligible.
  public FlagConfig(String[] args) throws IllegalArgumentException {
    if (args.length == 0) {
      remainingArgs = new String[0];
      return;
    }

    int argc = 0;
    while (args[argc].charAt(0) == '-') {
      String flagName = args[argc];
      // Ignore trivial flags (without raising an error).
      if (flagName.equals("-")) continue;
      // Strip off initial "-" repeatedly to get desired flag name.
      while (flagName.charAt(0) == '-') {
        flagName = flagName.substring(1, flagName.length());
      }

      try {
        Field field = FlagConfig.class.getField(flagName);

        // Parse String arguments.
        if (field.getType().getName().equals("java.lang.String")) {
          String flagValue;
          try {
            flagValue = args[argc + 1];
          } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("option -" + flagName + " requires an argument");
          }
          field.set(field, flagValue);
          // If there is an enum of accepted values, check that it's one of these.
          try {
            Field valuesField = FlagConfig.class.getField(flagName + "Values");
            String[] valuesList = (String[]) valuesField.get(FlagConfig.class);
            boolean found = false;
            for (int i = 0; i < valuesList.length; ++i) {
              if (flagValue.equals(valuesList[i])) {
                found = true;
                argc += 2;
                break;
              }
            }
            if (!found) {
              String errString = "Value '" + flagValue + "' not valid value for option -" + flagName
              + "\nValid values are: " + Arrays.toString(valuesList);
              throw new IllegalArgumentException(errString);
            }
          } catch (NoSuchFieldException e) {
            // This just means there isn't a list of allowed values.
            argc += 2;
          }
          // Parse String[] arguments, presuming they are comma-separated.
          // String[] arguments do not currently support fixed Value lists.
        } else if (field.getType().getName().equals("[Ljava.lang.String;")) {
          // All string values are lowercased.
          String flagValue;
          try {
            flagValue = args[argc + 1].toLowerCase();
          } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("option -" + flagName + " requires an argument");
          }
          field.set(field, flagValue.split(","));
          argc += 2;
        } else if (field.getType().getName().equals("int")) {
          // Parse int arguments.
          try {
            field.setInt(field, Integer.parseInt(args[argc + 1]));
          } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("option -" + flagName + " requires an argument");
          }
          argc += 2;
        } else if (field.getType().getName().equals("double")) {
          // Parse double arguments.
          try {
            field.setDouble(field, Double.parseDouble(args[argc + 1]));
          } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("option -" + flagName + " requires an argument");
          }
          argc += 2;
        } else if (field.getType().getName().equals("boolean")) {
          // Parse boolean arguments.
          field.setBoolean(field, true);
          ++argc;
        } else {
          logger.warning("No support for fields of type: "  + field.getType().getName());
          argc += 2;
        }
      } catch (NoSuchFieldException e) {
        throw new IllegalArgumentException("Command line flag not defined: " + flagName);
      } catch (IllegalAccessException e) {
        logger.warning("Must be able to access all fields publicly, including: " + flagName);
        e.printStackTrace();
      }

      if (argc >= args.length) {
        logger.fine("Consumed all command line input while parsing flags");
        makeFlagsCompatible();
        return;
      }
    }

    // Enforce constraints between flags.
    makeFlagsCompatible();

    // No more command line flags to parse. Trim args[] list and return.
    remainingArgs = new String[args.length - argc];
    for (int i = 0; i < args.length - argc; ++i) {
      remainingArgs[i] = args[argc + i];
    }
    return;
  }

  /**
   * Checks some interaction between flags, and fixes them up to make them compatible.
   * 
   * <br/>
   * In practice, this means:
   * <ul><li>If {@link vectortype} is {@code binary}, {@link dimension} is a multiple of 64,
   * or is increased to be become a multiple of 64.  {@link seedlength} is set to be half this
   * number.</li>
   * </ul>
   */
  private void makeFlagsCompatible() {
    if (vectortype.equals("binary")) {
      // Impose "multiple-of-64" constraint, to facilitate permutation of 64-bit chunks.
      if (dimension % 64 != 0) {
        dimension = (1 + (dimension / 64)) * 64;
        logger.warning("For performance reasons, dimensions for binary vectors must be a mutliple "
            + "of 64. Flags.dimension set to: " + dimension + ".");
      }
      // Impose "balanced binary vectors" constraint, to facilitate reasonable voting.
      if (seedlength != dimension / 2) {
        seedlength = dimension / 2;
        logger.warning("Binary vectors must be generated with a balanced number of zeros and ones."
            + " Flags.seedlength set to: " + seedlength + ".");
      }
    }
  }
}