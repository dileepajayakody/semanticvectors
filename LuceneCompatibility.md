_As of version 5.x, the main SemanticVectors jar distribution is built using Maven and includes its dependencies, so you don't have to install Lucene separately. However, if you have a different version of Lucene installed, you still need to follow the old instructions below to figure out which versions of SemanticVectors will work with your version of Lucene._

SemanticVectors depends on Apache Lucene, but not all versions of SemanticVectors work with all versions of Apache Lucene. To find the latest (and some older) versions of Apache Lucene click [here](http://lucene.apache.org/java/docs/index.html).

SV 5.8 works with (at least) Lucene 5.0.0.

SV 5.6 works with (at least) Lucene 4.10.0.

SV 5.4 works with (at least) Lucene 4.6.0.

SV 4.0 works with (at least) Lucene 4.5.0 and 4.3.1. (Versions in between these have not been checked explicitly.)

SV version 3.8 works with Lucene 3.6.x.

SV version 2.0 works with Lucene 3.0.3.

SV version 1.26 and earlier works with Lucene versions greater than 2.2.0 and higher, but not with Lucene 3.0.

SV version 1.30 and higher works with Lucene 3.0 and higher, but not with Lucene 2.X.



### Other Packages Involving Lucene ###

See SolrAndSemanticVectors if you're using SOLR to build your Lucene indexes.

With [OpenSuse](http://www.opensuse.org/) there have been some problems getting the default Lucene installation to work with SemanticVectors. So far the advice is to go with a direct Lucene install - see this [message thread](http://groups.google.com/group/semanticvectors/browse_thread/thread/7d121625e96d08f6?hl=en_US#).