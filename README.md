# Azure Cloud Application Design and Implementation Guidance

[![Join the chat at https://gitter.im/mspnp/azure-guidance](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/mspnp/azure-guidance?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

![](http://pnp.azurewebsites.net/images/pnp-logo.png)

Designing and implementing applications for the cloud brings a unique set of challenges due to the remoteness of the infrastructure and the very nature of distributed services. Azure provides a comprehensive platform and infrastructure for hosting large-scale web applications and cloud services. However, to be successful, you need to understand how to use the features that Azure provides to support your systems correctly. The purpose of this site is to provide architectural guidance to enable you to build and deploy world-class systems using Azure.

These documents focus on the essential aspects of architecting systems to make optimal use of Azure, and summarize best practice for building cloud solutions. The current set of guidance documents contains the following items:

----------

**Note** This is a living project. We will be adding more documentation to cover additional aspects of Azure architecture. We also welcome feedback, suggestions, and other contributions to those items that we have already documented.

----------

- **[API Design][APIDesign]** describes the issues that you should consider when designing a web API.

- **[API Implementation][APIImplementation]** focuses on recommended practices for implementing a web API and publishing it to make it available to client applications.

- **[Autoscaling Guidance][AutoscalingGuidance]** summarizes considerations for taking advantage of the elasticity of cloud-hosted environments while easing management overhead by reducing the need for an operator to continually monitor the performance of a system and make decisions about adding or removing resources.

- **[Background Jobs Guidance][BackgroundJobsGuidance]** describes the available options and recommended practices for implementing tasks that should be performed in the background, independently from any foreground or interactive operations.

- **[Content Delivery Network (CDN) Guidance][CDNGuidance]** provides general guidance and good practice for using the CDN to minimize the load on your applications, and maximize availability and performance.

- **[Caching Guidance][CachingGuidance]** summarizes how to use caching with Azure applications and services to improve the performance and scalability of a system.

- **[Data Partitioning Guidance][DataPartitioningGuidance]** describes strategies that you can use to partition data to improve scalability, reduce contention, and optimize performance.

- **[Monitoring and Diagnostics Guidance][MonitoringandDiagnosticsGuidance]** provides guidance on how to track the way in which users utilize your system, trace resource utilization, and generally monitor the health and performance of your system.

- **[Retry General Guidance][RetryGeneralGuidance]** covers general guidance for transient fault handling in an Azure application.

- **[Retry Service Specific Guidance][RetryServiceSpecificGuidance]** summarizes the retry mechanism features for the majority of Azure services, and includes information to help you use, adapt, or extend the retry mechanism for that service.

- **[Scalability Checklist][ScalabilityChecklist]** summarizes recommended practices for designing and implementing scalable services and handling data management.

- **[Availability Checklist][AvailabilityChecklist]** lists recommended practices for ensuring availability in an Azure application.

----------

**Note**. This documentation is oriented towards architects. You can find detailed code examples and implementation documentation on the [Azure website][AzureWebSite]. Additionally, the [Performance Optimization][PerformanceOptimization] site contains further guidance on how to design systems that are scalable and efficient under load.

----------

[AzureWebSite]: http://azure.microsoft.com/
[PerformanceOptimization]: https://github.com/mspnp/performance-optimization

[APIDesign]: API-Design.md
[APIImplementation]: API-Implementation.md
[AutoscalingGuidance]: Auto-scaling.md
[BackgroundJobsGuidance]: Background-Jobs.md
[CDNGuidance]: CDN.md
[CachingGuidance]: Caching.md
[DataPartitioningGuidance]: Data-partitioning.md
[MonitoringandDiagnosticsGuidance]: Monitoring.md
[RetryGeneralGuidance]: Retry-General.md
[RetryServiceSpecificGuidance]: Retry-Service-Specific.md
[RetryPolicies]: Retry-Policies.md
[ScalabilityChecklist]: Scalability-checklist.md
[AvailabilityChecklist]: availability-checklist.md
