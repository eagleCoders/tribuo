/*
 * Copyright (c) 2015-2020, Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tribuo.multilabel.sgd.objectives;

import com.oracle.labs.mlrg.olcut.provenance.ConfiguredObjectProvenance;
import com.oracle.labs.mlrg.olcut.provenance.impl.ConfiguredObjectProvenanceImpl;
import com.oracle.labs.mlrg.olcut.util.Pair;
import org.tribuo.math.util.SigmoidNormalizer;
import org.tribuo.multilabel.sgd.MultiLabelObjective;
import org.tribuo.math.la.SGDVector;
import org.tribuo.math.util.VectorNormalizer;

/**
 * A multilabel version of binary-cross entropy loss, which incorporates the sigmoid.
 * <p>
 * Generates a probabilistic model, and uses a {@link SigmoidNormalizer}.
 */
public class Sigmoid implements MultiLabelObjective {

    private final VectorNormalizer normalizer = new SigmoidNormalizer();

    /**
     * Returns a {@link Pair} of {@link Double} and the supplied prediction vector.
     * <p>
     * The prediction vector is transformed to produce the per label gradient.
     * @param truth The true label id
     * @param prediction The prediction for each label id
     * @return A Pair of the score and per label gradient.
     */
    @Override
    public Pair<Double,SGDVector> valueAndGradient(SGDVector truth, SGDVector prediction) {
        prediction.normalize(normalizer);
        /*
        double loss = Math.log(prediction.get(truth));
        prediction.scaleInPlace(-1.0);
        prediction.add(truth,1.0);
        */
        return new Pair<>(0.0,prediction);
    }

    @Override
    public VectorNormalizer getNormalizer() {
        return new SigmoidNormalizer();
    }

    /**
     * Returns true.
     * @return True.
     */
    @Override
    public boolean isProbabilistic() {
        return true;
    }

    @Override
    public String toString() {
        return "Sigmoid";
    }

    @Override
    public ConfiguredObjectProvenance getProvenance() {
        return new ConfiguredObjectProvenanceImpl(this,"MultiLabelObjective");
    }
}