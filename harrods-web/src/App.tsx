import {Admin, Resource,} from "react-admin";
import {harrodsDataProvider} from "../provider/HarrodsProvider";

export const App = () => <Admin dataProvider={harrodsDataProvider}>
    <Resource name="templates" />
</Admin>;
