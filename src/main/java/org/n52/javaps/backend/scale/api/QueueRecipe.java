package org.n52.javaps.backend.scale.api;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author <a href="mailto:e.h.juerrens@52north.org">J&uuml;rrens, Eike Hinderk</a>
 *
 * @since 1.4.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "recipe_type_id",
    "recipe_data"
})
public class QueueRecipe implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = -9164919847349950376L;

    @JsonProperty("recipe_type_id")
    private Integer recipeTypeId;

    @JsonProperty("recipe_data")
    private RecipeData recipeData;

    public QueueRecipe withRecipeTypeId(Integer recipeTypeId) {
        this.recipeTypeId = recipeTypeId;
        return this;
    }

    @JsonProperty("recipe_type_id")
    public Integer getRecipeTypeId() {
        return recipeTypeId;
    }

    @JsonProperty("recipe_type_id")
    public void setRecipeTypeId(Integer recipeTypeId) {
        this.recipeTypeId = recipeTypeId;
    }

    public QueueRecipe withRecipeData(RecipeData recipeData) {
        this.recipeData = recipeData;
        return this;
    }

    @JsonProperty("recipe_data")
    public RecipeData getRecipeData() {
        return recipeData;
    }

    @JsonProperty("recipe_data")
    public void setRecipeData(RecipeData recipeData) {
        this.recipeData = recipeData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("recipeTypeId", recipeTypeId)
                .append("recipeData", recipeData)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(recipeTypeId)
                .append(recipeData)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof QueueRecipe == false) {
            return false;
        }
        QueueRecipe rhs = (QueueRecipe) other;
        return new EqualsBuilder()
                .append(recipeTypeId, rhs.recipeTypeId)
                .append(recipeData, rhs.recipeData)
                .isEquals();
    }


}
