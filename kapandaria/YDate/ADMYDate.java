/* This is free and unencumbered software released into the public domain.
 *
 * THIS SOFTWARE IS PROVIDED THE CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE CONTRIBUTORS BE LIABLE FOR ANY 
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; BUSINESS
 * INTERRUPTION; OR ANY SPIRITUAL DAMAGE) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package kapandaria.YDate;

/**
 * ADMYDate class is abstract class for date management. it is based on GDN (Genesis Day Number), 
 * and is suitable for dates based on Day, Month and Year.
 * @author Orr Dvori &lt;dvoreader@gmail.com&gt;
 * @version 4.0.10
 */
public abstract class ADMYDate extends ADate
{
    public abstract int year();
    public abstract int month();
    public abstract int dayInMonth();
    public abstract int dayInYear();
    public abstract int yearLength();
    public abstract int monthLength();
    public abstract int previousMonthLength();
    public abstract int yearFirstDayGDN();
    public abstract int monthFirstDayGDN();
    
}
