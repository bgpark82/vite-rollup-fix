import {combineDataProviders} from "react-admin";
import databricksProvider from "./DatabricksProvider";
import {harrodsDataProvider} from "./HarrodsProvider";


/**
 * Resource에 따라 적절한 Provider를 선택하는 Proxy
 * @see https://marmelab.com/react-admin/DataProviders.html#combining-data-providers
 */
// @ts-ignore
export const dataProvider = combineDataProviders((resource: any) => {
    if (resource.startsWith("databricks")) {
        return databricksProvider
    }
    return harrodsDataProvider
});
