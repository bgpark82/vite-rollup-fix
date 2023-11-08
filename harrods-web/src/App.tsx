import {Admin, Resource,} from "react-admin";
import {harrodsDataProvider} from "../provider/HarrodsProvider";
import {TemplateList} from "./templates/TemplateList";

export const App = () => <Admin dataProvider={harrodsDataProvider}>
    <Resource name="templates" list={TemplateList} />
</Admin>;
