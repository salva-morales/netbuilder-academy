package uk.co.netbuilder.academy.impl;

import javax.jcr.Repository;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.jcr.api.SlingRepository;

import uk.co.netbuilder.academy.HelloService;

/**
 * One implementation of the {@link HelloService}. Note that
 * the repository is injected, not retrieved.
 */
@Service
@Component(metatype = false)
public class HelloServiceImpl implements HelloService {
    
    @Reference
    private SlingRepository repository;

    public String getRepositoryName() {
    	String a="a";
        return repository.getDescriptor(Repository.REP_NAME_DESC);
    }

}
