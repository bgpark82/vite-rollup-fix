import {Admin, Resource,} from "react-admin";
import {harrodsDataProvider} from "./component/provider/HarrodsProvider";
import {TemplateList} from "./templates/TemplateList";
import {TemplateShow} from "./templates/TemplateShow";
import {QueryList} from "./queries/QueryList";
import {QueryShow} from "./queries/QueryShow";
import {TemplateCreate} from "./templates/TemplateCreate";

export const App = () => <Admin dataProvider={harrodsDataProvider}>
    <Resource name="templates" list={TemplateList} show={TemplateShow} create={TemplateCreate}/>
    <Resource name="queries" list={QueryList} show={QueryShow}/>
</Admin>;
