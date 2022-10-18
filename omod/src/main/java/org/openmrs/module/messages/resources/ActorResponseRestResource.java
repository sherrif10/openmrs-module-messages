package org.openmrs.module.messages.resources;

import java.util.Arrays;
import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.messages.api.model.ActorResponse;
import org.openmrs.module.messages.api.service.ActorResponseService;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

import io.swagger.models.Model;
import io.swagger.models.ModelImpl;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.DateProperty;
import io.swagger.models.properties.IntegerProperty;
import io.swagger.models.properties.RefProperty;
import io.swagger.models.properties.StringProperty;

import org.openmrs.module.webservices.rest.web.RestConstants;

/**
 * {@link Resource} for ActorResponse, supporting standard CRUD operations
 */
@Resource(name = RestConstants.VERSION_1 + "/actorResponse", supportedClass = ActorResponse.class, supportedOpenmrsVersions = { "1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.0.*", "2.1.*","2.2.*",
	"2.3.*", "2.4.*", "2.5.*", "2.6.*" })
public class ActorResponseRestResource extends DataDelegatingCrudResource<ActorResponse> {
    
    @Override
    public List<Representation> getAvailableRepresentations() {
      return Arrays.asList(Representation.DEFAULT, Representation.REF);
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        if (rep instanceof DefaultRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("id");
            description.addProperty("display");
            description.addProperty("patient", Representation.REF);
            description.addProperty("person", Representation.REF);
            description.addProperty("response");
            description.addProperty("textQuestion");
            description.addProperty("textResponse");
            description.addProperty("question");
            description.addProperty("sourceId");
            description.addProperty("sourceType");
            description.addProperty("answeredTime");
            description.addProperty("voided");
          
            description.addSelfLink();
            description.addLink("ref", ".?v=" + RestConstants.REPRESENTATION_REF);
            description.addSelfLink();
            return description;
        } else if (rep instanceof FullRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("id");
			description.addProperty("display");
            description.addProperty("patient", Representation.REF);
            description.addProperty("person", Representation.REF);
            description.addProperty("response");
            description.addProperty("textQuestion");
            description.addProperty("textResponse");
            description.addProperty("question");
            description.addProperty("response");
            description.addProperty("sourceType");
            description.addProperty("answeredTime");
            description.addProperty("voided");
			description.addProperty("auditInfo");
            description.addSelfLink();
            return description;
        } else if (rep instanceof RefRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("id");
            description.addProperty("patient",Representation.REF);
            description.addProperty("person",Representation.REF);
            description.addProperty("actor");
            description.addProperty("textQuestion");
            description.addProperty("textResponse");
            description.addProperty("question");
            description.addProperty("response");
            description.addSelfLink();
            return description;
        }
        return null;
    }
    
    /**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource#getCreatableProperties()
	 */
	@Override
    public DelegatingResourceDescription getCreatableProperties() {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addRequiredProperty("id");
        description.addProperty("person", Representation.REF);
        description.addRequiredProperty("patient");
        description.addRequiredProperty("actorResponseType");
        description.addProperty("actor");
        description.addProperty("textQuestion");
        description.addProperty("question");
        description.addProperty("textResponse");
        description.addProperty("response");
        description.addProperty("answeredTime");
        description.addProperty("sourceId");
        return description;
    }
    
    /**
	 * @throws org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource#getUpdatableProperties()
	 */
	@Override
    public DelegatingResourceDescription getUpdatableProperties() throws ResourceDoesNotSupportOperationException {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addRequiredProperty("id");
        description.addRequiredProperty("patient");
        description.addProperty("person");
        description.addRequiredProperty("actorResponseType");
        description.addProperty("actor");
        description.addProperty("textQuestion");
        description.addProperty("question");
        description.addProperty("textResponse");
        description.addProperty("response");
        description.addProperty("answeredTime");
        description.addProperty("sourceId");
        description.addProperty("sourceType");
        return description;
    }
    
    @Override
    public Model getGETModel(Representation rep) {
        ModelImpl model = (ModelImpl) super.getGETModel(rep);
        if (rep instanceof DefaultRepresentation || rep instanceof FullRepresentation) {
            model
                    .property("id", new StringProperty())
                    .property("patient", new StringProperty())
                    .property("person", new StringProperty())
                    .property("actorResponseType", new StringProperty())
                    .property("actor", new StringProperty())
                    .property("sourceId", new StringProperty())
                    .property("sourceType", new StringProperty())
                    .property("textResponse", new StringProperty())
                    .property("answeredTime", new DateProperty())
                    .property("question", new StringProperty())
                    .property("response", new StringProperty())
                    .property("attributes",new ArrayProperty(new RefProperty("#/definitions/ActorResponseAttributeGetRef")));

        }
        if (rep instanceof DefaultRepresentation) {
            model
                    .property("Response", new RefProperty("#/definitions/ResponseGetRef"))
                    .property("TextQuestion", new RefProperty("#/definitions/TextQuestionGetRef"))
                    .property("Actor", new RefProperty("#/definitions/ActorGetRef"))
                    .property("Question", new RefProperty("#/definitions/QuestionGetRef"));             

        } else if (rep instanceof FullRepresentation) {
            model
                    .property("Response", new RefProperty("#/definitions/ResponseGetRef"))
                    .property("TextQuestion", new RefProperty("#/definitions/TextQuestionGetRef"))
                    .property("Actor", new RefProperty("#/definitions/ActorGetRef"))
                    .property("Question", new RefProperty("#/definitions/QuestionGetRef"))
                    .property("AnsweredTime", new RefProperty("#/definitions/AnsweredTimeGetRef"))
                    .property("Question", new RefProperty("#/definitions/QuestionGetRef"))
                    .property("SourceType", new RefProperty("#/definitions/SourceTypeGetRef"))
                    .property("SourceId", new RefProperty("#/definitions/SourceIdGetRef"));
        }
        return model;
    }
    

    @Override
    public Model getCREATEModel(Representation representation) {
        ModelImpl model = new ModelImpl()
                .property("person", new ArrayProperty(new RefProperty("#/definitions/PersonCreate")))
                .property("patient", new ArrayProperty(new RefProperty("#/definitions/PatientCreate")))
                .property("id", new IntegerProperty())
                .property("answeredTime", new DateProperty())
                .property("actorResponseType",new ArrayProperty(new RefProperty("#/definitions/ActorResponseTypeAttributeCreate")))
                .property("question",new ArrayProperty(new RefProperty("#/definitions/ActorResponseTypeQuestionCreate")))
                .property("attributes", new ArrayProperty(new RefProperty("#/definitions/PersonAttributeCreate")));

        model.setRequired(Arrays.asList("question", "response"));
        return model;
    }
    
    @Override
    public Model getUPDATEModel(Representation representation) {
        ModelImpl model = new ModelImpl()
                .property("person", new ArrayProperty(new RefProperty("#/definitions/PersonCreate")))
                .property("patient", new ArrayProperty(new RefProperty("#/definitions/PatientCreate")))
                .property("sourceId", new StringProperty())
                .property("textResponse", new StringProperty())
                .property("id", new IntegerProperty())
                .property("answeredTime", new DateProperty())
                .property("deathDate", new DateProperty())
                .property("causeOfDeath", new StringProperty())
                .property("actorResponseType",
                        new ArrayProperty(new RefProperty("#/definitions/ActorResponseTypeAttributeCreate")))
                .property("question",
                        new ArrayProperty(new RefProperty("#/definitions/ActorResponseTypeQuestionCreate")))
                .property("attributes", new ArrayProperty(new RefProperty("#/definitions/PersonAttributeCreate")));

        model.setRequired(Arrays.asList("question", "response"));
        return model;
    }
    
    @Override
    public ActorResponse newDelegate() {
        return new ActorResponse();
    }

    @Override
    public boolean isRetirable() {
      return true;
    }

    @Override
    public ActorResponse save(ActorResponse delegate) {
        return Context.getService(ActorResponseService.class).saveOrUpdate(delegate);
    }
    
    @PropertyGetter("display")
	public String getDisplayString(ActorResponse response) {
        return response.getTextQuestion();
	}

    @Override
    protected void delete(ActorResponse response, String reason, RequestContext context) throws ResponseException {
       if (response != null) {
			return; //  delete is idempotent, so we return success here
		}
        Context.getService(ActorResponseService.class).delete(response);
    }

    @Override
    public ActorResponse getByUniqueId(String uuid) {
        return Context.getService(ActorResponseService.class).getByUuid(uuid);
    }

    @Override
    public void purge(ActorResponse delegate, RequestContext context) throws ResponseException {
        if (delegate == null) {
            return;
        }
        Context.getService(ActorResponseService.class).delete(delegate);
    }
  
    @Override
    protected PageableResult doGetAll(RequestContext context) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException();
    }

}
    
