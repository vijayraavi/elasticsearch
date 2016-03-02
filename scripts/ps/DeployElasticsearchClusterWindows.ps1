Login-AzureRmAccount
Select-AzureRmSubscription -SubscriptionName 'Pnp Azure'

$resourceGroupName='my-resource-group-name'
$deploymentName='my-deployment-name'

# Change the location accordingly - supported values may be obtained using REST call below:
# https://management.core.windows.net/<subscription-id>/locations
$location='West US' 

# Omit the below step if you are using an existing resource group
New-AzureRmResourceGroup -Name $resourceGroupName -Location $location

$parameters=@{
    adminUserName='changeme'
    adminPassword='changeme'
    virtualNetworkName='my-vnet-name'
    esClusterName='my-elasticsearch-cluster-name'
    loadBalancerType='external'
    vmSizeDataNodes='Standard_D2_v2' # Find supported sizes in a region using ps command e.g. "Get-AzureRmVMSize -Location 'West US' | Select Name"
    vmClientNodeCount=0
    vmDataNodeCount=3
    OS='windows' # Supported values are 'windows' and 'ubuntu'
    esVersion='2.2.0'
    marvel='yes'
    kibana='no'
}

$templateUri = "https://raw.githubusercontent.com/Azure/azure-quickstart-templates/master/elasticsearch/azuredeploy.json"

Test-AzureRmResourceGroupDeployment -ResourceGroupName $resourceGroupName -TemplateUri $templateUri -TemplateParameterObject $parameters -Verbose
New-AzureRmResourceGroupDeployment -Name $deploymentName -ResourceGroupName $resourceGroupName -TemplateUri $templateUri -TemplateParameterObject $parameters -Verbose
