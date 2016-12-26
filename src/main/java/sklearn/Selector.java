/*
 * Copyright (c) 2016 Villu Ruusmann
 *
 * This file is part of JPMML-SkLearn
 *
 * JPMML-SkLearn is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JPMML-SkLearn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with JPMML-SkLearn.  If not, see <http://www.gnu.org/licenses/>.
 */
package sklearn;

import java.util.ArrayList;
import java.util.List;

import org.dmg.pmml.DataType;
import org.dmg.pmml.OpType;
import org.jpmml.converter.Feature;
import org.jpmml.sklearn.FeatureMapper;

abstract
public class Selector extends Transformer implements HasNumberOfFeatures {

	public Selector(String module, String name){
		super(module, name);
	}

	abstract
	public int[] selectFeatures(List<Feature> features);

	@Override
	public OpType getOpType(){
		return null;
	}

	@Override
	public DataType getDataType(){
		return null;
	}

	@Override
	public List<Feature> encodeFeatures(List<String> ids, List<Feature> features, FeatureMapper featureMapper){
		int[] selection = selectFeatures(features);

		if(selection == null){
			return features;
		}

		List<String> selectedIds = new ArrayList<>();
		List<Feature> selectedFeatures = new ArrayList<>();

		for(int i = 0; i < selection.length; i++){
			int index = selection[i];

			selectedIds.add(ids.get(index));
			selectedFeatures.add(features.get(index));
		}

		ids.clear();

		if(selectedIds.size() > 0){
			ids.addAll(selectedIds);
		}

		return selectedFeatures;
	}
}