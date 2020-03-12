package com.triadsoft;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;

import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.ecr.IRepository;
import software.amazon.awscdk.services.ecr.Repository;
import software.amazon.awscdk.services.ecs.*;
import software.amazon.awscdk.services.ecs.patterns.*;

public class TasksInfraStack extends Stack {
    public TasksInfraStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public TasksInfraStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        Vpc vpc = Vpc.Builder.create(this, "tasks-vpc-dev")
                .maxAzs(3)  // Default is all AZs in region
                .build();

        Cluster cluster = Cluster.Builder
                .create(this, "tasks-cluster-dev")
                .vpc(vpc).build();

        IRepository repository = Repository.fromRepositoryName(this,"task-api","task-api");
        //Create a load-balanced Fargate service and make it public
        ApplicationLoadBalancedFargateService.Builder.create(this, "tasks-service-dev")
                .cluster(cluster)           // Required
                .cpu(512)                   // Default is 256
                .desiredCount(4)            // Default is 1
                .taskImageOptions(
                        ApplicationLoadBalancedTaskImageOptions.builder()
                                //.image(ContainerImage.fromRegistry("amazon/amazon-ecs-sample"))
                                .image(ContainerImage.fromEcrRepository(repository,"latest"))
                                .build())
                .memoryLimitMiB(2048)       // Default is 512
                .publicLoadBalancer(true)   // Default is false
                .build();
    }
}
