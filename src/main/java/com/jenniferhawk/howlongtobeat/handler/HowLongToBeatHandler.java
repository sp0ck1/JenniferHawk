package com.jenniferhawk.howlongtobeat.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;

import com.jenniferhawk.howlongtobeat.HowLongToBeatEntry;
import com.jenniferhawk.howlongtobeat.HowLongToBeatSearchResultPage;
import com.jenniferhawk.howlongtobeat.HowLongToBeatService;


    /**
     * {@link RestController} for handling request for the
     * {@link HowLongToBeatService}. Requests to this controllers' endpoint are
     * returning a generic {@link ResultResponseEntity} object as json, containing a
     * <b>>result</b> property
     *
     * @author Christian Katzorke ckatzorke@gmail.com
     *
     */
    @RestController
    public class HowLongToBeatHandler {

        private final HowLongToBeatService howLongToBeatService;

        @Autowired
        public HowLongToBeatHandler(HowLongToBeatService howlongtobeatService) {
            this.howLongToBeatService = howlongtobeatService;
        }

        /**
         * Endpoint <b>/rest/howlongtobeat</b> that performs a search against the
         * search interface of {@link HowLongToBeatService}. See
         * {@link HowLongToBeatService} for more details.
         *
         * @param game
         *            the searchinput text, like &quot;Dark Souls&quot;
         * @param request
         *            injected HttpRequest
         * @return result, containing a {@link HowLongToBeatSearchResultPage} as
         *         <b>result</b> property
         */
        @Timed
        @RequestMapping(value = "/rest/howlongtobeat", method = RequestMethod.GET, produces = {
                MediaType.APPLICATION_JSON_VALUE })
        public ResultResponseEntity<HowLongToBeatSearchResultPage> howLongToBeat(@RequestParam String game, HttpServletRequest request) {
            ResultResponseEntity<HowLongToBeatSearchResultPage> result = new ResultResponseEntity<>(this.howLongToBeatService.search(game));
            result.add(new Link(request.getRequestURI() + "?" + request.getQueryString()));
            // TODO example?
            return result;
        }

        /**
         * Endpoint <b>/rest/howlongtobeatdetail</b> that fetches a specific game
         * detail via the interface of {@link HowLongToBeatService}
         *
         * @param gameId
         *            the id of the game, as used by the webpage
         *            <a href="http://howlongtobeat.com">Howlongtobeat</a>, like
         *            &quot;16624&quot; for <b>Bioshock Infinit: Burial at the Sea
         *            DLC</b>
         * @param request
         *            injected HttpRequest
         * @return result, containing a {@link HowLongToBeatEntry} as <b>result</b>
         *         property
         */
        @Timed
        @RequestMapping(value = "/rest/howlongtobeatdetail", method = RequestMethod.GET, produces = {
                MediaType.APPLICATION_JSON_VALUE })
        public ResultResponseEntity<HowLongToBeatEntry> howLongToBeatDetails(@RequestParam String gameId, HttpServletRequest request) {
            ResultResponseEntity<HowLongToBeatEntry> result = new ResultResponseEntity<>(this.howLongToBeatService.detail(gameId));
            result.add(new Link(request.getRequestURI() + "?" + request.getQueryString()));
            return result;
        }

    }


