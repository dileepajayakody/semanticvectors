/**
   Copyright (c) 2007, University of Pittsburgh

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

package pitt.search.semanticvectors.vectors;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

/**
 * This class provides some standard vector methods, including orthogonalization for
 * real and complex vectors. Many old methods have been removed and are now implemented
 * in vector classes for real, complex, and binary vectors.
 */
public class VectorUtils {
  private static final Logger logger = Logger.getLogger(VectorUtils.class.getCanonicalName());

  /**
   * Get nearest vector from list of candidates.
   * @param vector The vector whose nearest neighbor is to be found.
   * @param candidates The list of vectors from whoe the nearest is to be chosen.
   * @return Integer value referencing the position in the candidate list of the nearest vector.
   */
  public static int getNearestVector(Vector vector, Vector[] candidates) {
    int nearest = 0;
    double maxSim = vector.measureOverlap(candidates[0]);
    for (int i = 1; i < candidates.length; ++i) {
      double thisDist = vector.measureOverlap(candidates[i]);
      if (thisDist > maxSim) {
        maxSim = thisDist;
        nearest = i;
      }
    }
    return nearest;
  }

  public static double compareWithProjection(Vector testVector, ArrayList<Vector> vectors) {
    float score = 0;
    for (int i = 0; i < vectors.size(); ++i) {
      score += Math.pow(testVector.measureOverlap(vectors.get(i)), 2);
    }
    return (float) Math.sqrt(score);
  }

  /**
   * The orthogonalize function takes an array of vectors and
   * orthogonalizes them using the Gram-Schmidt process. The vectors
   * are orthogonalized in place, so there is no return value.  Note
   * that the output of this function is order dependent, in
   * particular, the jth vector in the array will be made orthogonal
   * to all the previous vectors. Since this means that the last
   * vector is orthogonal to all the others, this can be used as a
   * negation function to give an vector for
   * vectors[last] NOT (vectors[0] OR ... OR vectors[last - 1].
   *
   * @param vectors ArrayList of vectors (which are themselves arrays of
   * floats) to be orthogonalized in place.
   */
  public static boolean orthogonalizeVectors(ArrayList<Vector> vectors) {    
    int dimension = vectors.get(0).getDimension();
    // Go up through vectors in turn, parameterized by k.
    for (int k = 0; k < vectors.size(); ++k) {
      Vector kthVector = vectors.get(k);
      kthVector.normalize();
      if (kthVector.getDimension() != dimension) {
        logger.warning("In orthogonalizeVector: not all vectors have required dimension.");
        return false;
      }
      // Go up to vector k, parameterized by j.
      for (int j = 0; j < k; ++j) {
        Vector jthVector = vectors.get(j);
        double dotProduct = kthVector.measureOverlap(jthVector);
        // Subtract relevant amount from kth vector.
        kthVector.superpose(jthVector, -dotProduct, null);
        // And renormalize each time.
        kthVector.normalize();
      }
    }
    return true;
  }

  /**
   * Generates a basic sparse vector
   * with mainly zeros and some 1 and -1 entries (seedLength/2 of each)
   * each vector is an array of length seedLength containing 1+ the index of a non-zero
   * value, signed according to whether this is a + or -1.
   * <br>
   * e.g. +20 would indicate a +1 in position 19, +1 would indicate a +1 in position 0.
   *      -20 would indicate a -1 in position 19, -1 would indicate a -1 in position 0.
   * <br>
   * The extra offset of +1 is because position 0 would be unsigned,
   * and would therefore be wasted. Consequently we've chosen to make
   * the code slightly more complicated to make the implementation
   * slightly more space efficient.
   *
   * @return Sparse representation of basic ternary vector. Array of
   * short signed integers, indices to the array locations where a
   * +/-1 entry is located.
   */
  public static short[] generateRandomVector(int seedLength, int dimension, Random random) {
    boolean[] randVector = new boolean[dimension];
    short[] randIndex = new short[seedLength];

    int testPlace, entryCount = 0;

    /* put in +1 entries */
    while (entryCount < seedLength / 2) {
      testPlace = random.nextInt(dimension);
      if (!randVector[testPlace]) {
        randVector[testPlace] = true;
        randIndex[entryCount] = new Integer(testPlace + 1).shortValue();
        entryCount++;
      }
    }

    /* put in -1 entries */
    while (entryCount < seedLength) {
      testPlace = random.nextInt (dimension);
      if (!randVector[testPlace]) {
        randVector[testPlace] = true;
        randIndex[entryCount] = new Integer((1 + testPlace) * -1).shortValue();
        entryCount++;
      }
    }

    return randIndex;
  }
}