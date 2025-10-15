/*
 * @(#)NoNullsPredicate.java   2010.01.13 at 11:50:52 PST
 *
 * Copyright 2009 MBARI
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package org.mbari.expd.actions;
import java.util.function.Predicate;


/**
 * Predicate used for filtering out null objects
 * @author brian
 */
public class NoNullsPredicate implements Predicate<Object> {

    /**
     *
     * @param input
     * @return
     */
    @Override
    public boolean test(Object input) {
        return input != null;
    }
}
