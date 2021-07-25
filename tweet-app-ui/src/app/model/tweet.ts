import { Reply } from "./reply";

export interface Tweet {
    id: string;
    loginID: string;
    tweetMessage: string;
    reply: Reply[];
    likes: string[];
    modifiedDate: Date;

}
